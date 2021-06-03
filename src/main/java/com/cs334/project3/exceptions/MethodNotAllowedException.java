package com.cs334.project3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

// 405 METHOD NOT ALLOWED Error
@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowedException extends ResponseStatusException {
    public MethodNotAllowedException(){
        super(HttpStatus.METHOD_NOT_ALLOWED);
    }
    public MethodNotAllowedException(String message){
        super(HttpStatus.METHOD_NOT_ALLOWED, message);
    }
}
