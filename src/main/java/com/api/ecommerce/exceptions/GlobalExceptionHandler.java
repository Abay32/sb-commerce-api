package com.api.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value=UserNotFoundException.class)
    public ResponseEntity<ErrorObject>  handleUserNotFoundException(UserNotFoundException e, WebRequest request){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setError(e.getMessage());
        errorObject.setStatus(HttpStatus.NOT_FOUND.value());
        errorObject.setTimestamp(new Date());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }
}
