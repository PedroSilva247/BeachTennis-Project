package com.example.ProjectBeachTennis.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<String> handlerEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(EntityNonExistsException.class)
    public ResponseEntity<String> handlerEntityNonExistsException(EntityNonExistsException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
