package com.cs334.project3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

// 404 NOT FOUND Error
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends ResponseStatusException {
    public ResourceNotFoundException(){
        super(HttpStatus.NOT_FOUND);
    }
    public ResourceNotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }
}
