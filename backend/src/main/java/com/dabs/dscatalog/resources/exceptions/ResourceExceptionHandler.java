package com.dabs.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dabs.dscatalog.services.exceptions.EntityNotFoundException;

@ControllerAdvice//permite que essa classe intercepte as exceções na camada de resource e trate elas
public class ResourceExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)//sempre que algum dos seus controladores REST acontecer uma exceção desse tipo, esse método irá tratar dela
	public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource not Found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	

}
