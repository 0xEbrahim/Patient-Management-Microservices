package com.pm.patientservice.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(NotFoundException.class)
public ResponseEntity<Map<String,String>> handleNotFound(NotFoundException ex){
    Map<String,String> map = new HashMap<>();
    log.warn(ex.getMessage());
    map.put("message",ex.getMessage());
    return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
}

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExistsException(AlreadyExistsException ex) {
        Map<String,String> errors = new HashMap<>();
        log.warn(ex.getMessage());
        errors.put("message", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        log.warn("Field validation failed");
        ex.getBindingResult().getAllErrors().forEach((error) ->

                errors.put(((FieldError) error).getField(), error.getDefaultMessage())

        );
        return ResponseEntity.badRequest().body(errors);
    }
}
