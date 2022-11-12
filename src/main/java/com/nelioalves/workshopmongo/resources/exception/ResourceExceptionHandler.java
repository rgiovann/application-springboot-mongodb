package com.nelioalves.workshopmongo.resources.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nelioalves.workshopmongo.services.exception.ObjectNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

// this annotation intercepts exceptions to be handled by the class below
@ControllerAdvice
public class ResourceExceptionHandler {

	// Annotation for handling exceptions in specific handler classes and/orhandler methods. 
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		String error = "Resource not found.";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
//	@ExceptionHandler(DatabaseException.class)
//	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
//		String error = "Database error.";
//		HttpStatus status = HttpStatus.BAD_REQUEST;
//		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),request.getRequestURI());
//		return ResponseEntity.status(status).body(err);
//	}	
	
    //  handle bad JSON formats in PUT/POST messages
 	@ExceptionHandler(HttpMessageNotReadableException.class)
 	public ResponseEntity<StandardError> jsonError(RuntimeException e, HttpServletRequest request){
 		String error = "JSON error.";
 	HttpStatus status = HttpStatus.BAD_REQUEST;
 		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),request.getRequestURI());
 		return ResponseEntity.status(status).body(err);
 	}	
 	
	
}
