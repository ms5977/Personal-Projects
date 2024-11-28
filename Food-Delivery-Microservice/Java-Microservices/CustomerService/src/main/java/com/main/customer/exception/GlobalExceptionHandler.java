package com.main.customer.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	// Logger instance
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		logger.error("Validation failed: {}", ex.getMessage(), ex);

		Map<String, Object> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		errors.put("status", HttpStatus.BAD_REQUEST.value());
		errors.put("error", "Validation Failed");
		errors.put("timeStamp", LocalDateTime.now().format(formatter));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		logger.error("Data integrity violation: {}", ex.getMessage(), ex);

		Map<String, Object> errors = new HashMap<>();
		errors.put("message", "A conflict occurred due to data integrity violation. Please check your input.");
		errors.put("details", ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage());
		errors.put("timeStamp", LocalDateTime.now().format(formatter));
		errors.put("status", HttpStatus.CONFLICT.value());
		return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		logger.warn("Resource not found: {}", ex.getMessage(), ex);

		Map<String, Object> errors = new HashMap<>();
		errors.put("message", "The requested resource was not found.");
		errors.put("details", ex.getMessage());
		errors.put("timeStamp", LocalDateTime.now().format(formatter));
		errors.put("status", HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicateResourceException(DuplicateResourceException ex) {
		logger.warn("Duplicate resource error: {}", ex.getMessage(), ex);

		Map<String, Object> error = new HashMap<>();
		error.put("message", "A resource with the provided information already exists.");
		error.put("details", ex.getMessage());
		error.put("timeStamp", LocalDateTime.now().format(formatter));
		error.put("status", HttpStatus.CONFLICT.value());
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNoResourceFoundException(NoResourceFoundException ex) {
		logger.warn("No resource found: {}", ex.getMessage(), ex);

		Map<String, Object> error = new HashMap<>();
		error.put("message", "No resource could be found matching the provided criteria.");
		error.put("details", ex.getMessage());
		error.put("timeStamp", LocalDateTime.now().format(formatter));
		error.put("status", HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
		logger.error("Method not supported: {}", ex.getMessage(), ex);

		Map<String, Object> errors = new HashMap<>();
		String message = String.format("HTTP method '%s' is not supported for this endpoint. Supported methods are: %s",
				ex.getMethod(), ex.getSupportedHttpMethods());
		errors.put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
		errors.put("error", "Method Not Allowed");
		errors.put("message", message);
		errors.put("timeStamp", LocalDateTime.now().format(formatter));
		return new ResponseEntity<>(errors, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
		logger.error("Illegal argument exception: {}", ex.getMessage(), ex);

		Map<String, Object> error = new HashMap<>();
		error.put("details", ex.getMessage());
		error.put("timeStamp", LocalDateTime.now().format(formatter));
		error.put("status", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
