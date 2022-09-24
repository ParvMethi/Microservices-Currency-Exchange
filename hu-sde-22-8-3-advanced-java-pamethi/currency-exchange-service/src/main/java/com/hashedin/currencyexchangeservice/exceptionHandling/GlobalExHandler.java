package com.hashedin.currencyexchangeservice.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExHandler {

@ExceptionHandler(ResourceException.class)
	public ResponseEntity<Object> resourceExceptionHandling(ResourceException exception, WebRequest request){
		ErrorDetails errorDetails =
				new ErrorDetails(exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}


}
