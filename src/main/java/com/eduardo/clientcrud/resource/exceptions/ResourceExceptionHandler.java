package com.eduardo.clientcrud.resource.exceptions;

import com.eduardo.clientcrud.service.exceptions.DatabaseException;
import com.eduardo.clientcrud.service.exceptions.ResourceAlreadyExistsException;
import com.eduardo.clientcrud.service.exceptions.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest req){
        HttpStatus status = HttpStatus.NOT_FOUND;
        String errorStr = "Resource not found";
        return buildError(errorStr, e.getMessage(), req.getRequestURI(), status);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<StandardError> resourceAlreadyExists(ResourceAlreadyExistsException e, HttpServletRequest req){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorStr = "Resource already exists";
        return buildError(errorStr, e.getMessage(), req.getRequestURI(), status);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> databaseException(DatabaseException e, HttpServletRequest req){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorStr = "Database exception";
        return buildError(errorStr, e.getMessage(), req.getRequestURI(), status);
    }

    private ResponseEntity<StandardError> buildError(String errorStr, String message, String requestURI, HttpStatus status) {
        StandardError error = new StandardError();
        error.setError(errorStr);
        error.setMessage(message);
        error.setPath(requestURI);
        error.setStatus(status.value());
        error.setTimestamp(Instant.now());
        return ResponseEntity.status(status).body(error);
    }

}
