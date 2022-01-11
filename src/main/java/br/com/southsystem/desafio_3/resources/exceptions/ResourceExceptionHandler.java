package br.com.southsystem.desafio_3.resources.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.southsystem.desafio_3.services.exceptions.DatabaseException;
import br.com.southsystem.desafio_3.services.exceptions.FileReaderException;
import br.com.southsystem.desafio_3.services.exceptions.NegativeValueException;
import br.com.southsystem.desafio_3.services.exceptions.NullValueException;
import br.com.southsystem.desafio_3.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> handleValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest request) {
	    Map<String, String> errors = new HashMap<>();
	    HttpStatus status = HttpStatus.BAD_REQUEST;
	    
	    e.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    
	    StandardError err = new StandardError(Instant.now(), status.value(), errors, request.getRequestURI());
	    return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		errors.put(error, e.getMessage());
		StandardError err = new StandardError(Instant.now(), status.value(), errors, request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		errors.put(error, e.getMessage());
		StandardError err = new StandardError(Instant.now(), status.value(), errors, request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(NegativeValueException.class)
	public ResponseEntity<StandardError> negativeValue(NegativeValueException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		String error = "Negative value error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		errors.put(error, e.getMessage());
		StandardError err = new StandardError(Instant.now(), status.value(), errors, request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(NullValueException.class)
	public ResponseEntity<StandardError> negativeValue(NullValueException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		String error = "Null value error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		errors.put(error, e.getMessage());
		StandardError err = new StandardError(Instant.now(), status.value(), errors, request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<StandardError> accessDenied(AccessDeniedException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		String error = "Access denied";
		HttpStatus status = HttpStatus.FORBIDDEN;
		errors.put(error, e.getMessage());
		StandardError err = new StandardError(Instant.now(), status.value(), errors, request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(FileReaderException.class)
	public ResponseEntity<StandardError> iO(FileReaderException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		String error = "Load file error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		errors.put(error, e.getMessage());
		StandardError err = new StandardError(Instant.now(), status.value(), errors, request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

}
