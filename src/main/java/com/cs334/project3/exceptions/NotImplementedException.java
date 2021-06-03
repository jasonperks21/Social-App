package com.cs334.project3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

// 501 NOT IMPLEMENTED Error
@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
public class NotImplementedException extends ResponseStatusException {
    public NotImplementedException(){
        super(HttpStatus.NOT_IMPLEMENTED);
    }
    public NotImplementedException(String message){
        super(HttpStatus.NOT_IMPLEMENTED, message);
    }
}