package com.blog.main.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.blog.main.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse>resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String message=ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,false,LocalDateTime.now(),HttpStatus.NOT_FOUND);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>>handleMethodArgsNotValidException(MethodArgumentNotValidException ex)
	{
		Map<String, String>resp=new HashMap<String, String>();
		
		ex.getBindingResult().getAllErrors().forEach(error->{
			String fieldName = ((FieldError)error).getField();
			System.out.println("Field Name:"+fieldName);
			String errorMessage = error.getDefaultMessage();
			System.out.println("ErrorMessage:"+errorMessage);
			resp.put(fieldName, errorMessage);
		});
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, Object>>handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex)
	{
		Map<String, Object>errorDetails=new HashMap<String, Object>();
		errorDetails.put("error", "Please Provide the Id to Delete");
		errorDetails.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		errorDetails.put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
		errorDetails.put("message", ex.getMessage());
		errorDetails.put("path", "/api/categories");
		
		return new ResponseEntity<Map<String,Object>>(errorDetails,HttpStatus.METHOD_NOT_ALLOWED);
	}
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<Map<String, Object>>handleNoResourceFoundException(NoResourceFoundException ex)
	{
		Map<String, Object>errorDetails=new HashMap<String, Object>();
		errorDetails.put("error", "Please Provide the Id to Delete");
		errorDetails.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		errorDetails.put("status", HttpStatus.NOT_FOUND.value());
		errorDetails.put("message", ex.getMessage());
		errorDetails.put("path" ,ex.getResourcePath());
		
		return new ResponseEntity<Map<String,Object>>(errorDetails,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse>handleException(ApiException ex)
	{
		String message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message,true,LocalDateTime.now(),HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
	}
}
