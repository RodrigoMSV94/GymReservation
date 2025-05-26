package com.mitocode.reservation.adapter.in.rest.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class ClientErrorHandler {

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ErrorEntity> handleClassNotFoundException(ClientErrorException e){
        return e.getResponse();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorEntity> handleAccessDeniedException(AccessDeniedException e) {
        ErrorEntity error = new ErrorEntity(HttpStatus.FORBIDDEN.value(), "You do not have permission to access this resource.");
        return ResponseEntity.status(403).body(error);
    }
}
