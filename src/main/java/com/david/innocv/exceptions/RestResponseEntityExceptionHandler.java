package com.david.innocv.exceptions;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	private static final Logger LOGGER=LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);
	
	@ExceptionHandler(value = {IllegalArgumentException.class,
							   JdbcSQLIntegrityConstraintViolationException.class})
	public ResponseEntity<Object> handleConflicts(Exception ex, WebRequest request)
	{
		String responseBody = ex.getMessage();
		LOGGER.error(responseBody);
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler(value = {ResourceNotFoundException.class})
	public ResponseEntity<Object> handleResourceNotFound(Exception ex, WebRequest request)
	{
		String responseBody = ex.getMessage();
		LOGGER.error(responseBody);
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
}
