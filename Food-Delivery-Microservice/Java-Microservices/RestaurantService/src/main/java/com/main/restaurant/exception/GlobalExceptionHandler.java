package com.main.restaurant.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		Map<String, Object> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		errors.put("status", HttpStatus.BAD_REQUEST.value());
		errors.put("error", "Validation Failed");
		errors.put("timeStamp", LocalDateTime.now().format(formatter));
		logger.warn("Validation error occurred: {}", errors);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("message", "A conflict occurred due to data integrity violation. Please check your input.");
		errors.put("details", ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage());
		errors.put("timeStamp", LocalDateTime.now().format(formatter));
		errors.put("status", HttpStatus.CONFLICT.value());
		logger.error("Data integrity violation: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("message", "The requested resource was not found.");
		errors.put("details", ex.getMessage());
		errors.put("timeStamp", LocalDateTime.now().format(formatter));
		errors.put("status", HttpStatus.NOT_FOUND.value());
		logger.warn("Resource not found: {}", ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicateResourceException(DuplicateResourceException ex) {
		Map<String, Object> error = new HashMap<>();
		error.put("message", "A resource with the provided information already exists.");
		error.put("details", ex.getMessage());
		error.put("timeStamp", LocalDateTime.now().format(formatter));
		error.put("status", HttpStatus.CONFLICT.value());
		logger.info("Duplicate resource detected: {}", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNoResourceFoundException(NoResourceFoundException ex) {
		Map<String, Object> error = new HashMap<>();
		error.put("message", "No resource could be found matching the provided criteria.");
		error.put("details", ex.getMessage());
		error.put("timeStamp", LocalDateTime.now().format(formatter));
		error.put("status", HttpStatus.NOT_FOUND.value());
		logger.warn("No resource found: {}", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
		Map<String, Object> errors = new HashMap<>();
		String message = String.format("HTTP method '%s' is not supported for this endpoint. Supported methods are: %s",
				ex.getMethod(), ex.getSupportedHttpMethods());
		errors.put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
		errors.put("error", "Method Not Allowed");
		errors.put("message", message);
		errors.put("timeStamp", LocalDateTime.now().format(formatter));
		logger.warn("Method not allowed: {}", message);
		return new ResponseEntity<>(errors, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
		Map<String, Object> error = new HashMap<>();
		error.put("details", ex.getMessage());
		error.put("timeStamp", LocalDateTime.now().format(formatter));
		error.put("status", HttpStatus.BAD_REQUEST.value());
		logger.warn("Illegal argument exception: {}", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations().forEach(constraintViolation -> {
			String fieldName = constraintViolation.getPropertyPath().toString();
			String errorMessage = constraintViolation.getMessage();
			errors.put(fieldName, errorMessage);
		});
		logger.warn("Constraint violations detected: {}", errors);
		return ResponseEntity.badRequest().body(errors);
	}
}
