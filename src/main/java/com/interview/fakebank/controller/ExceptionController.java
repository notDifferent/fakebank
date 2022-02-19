package com.interview.fakebank.controller;

import com.interview.fakebank.model.CustomError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    private final Logger logger = LogManager.getLogger();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        logger.error("exception occurred", exception);
        return new ResponseEntity<>(new CustomError(exception.getFieldError().getField(), exception.getFieldError().getDefaultMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception exception) {
        logger.error("exception occurred", exception);
        return new ResponseEntity<>(new CustomError(null, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
