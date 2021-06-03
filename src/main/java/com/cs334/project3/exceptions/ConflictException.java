package com.cs334.project3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

// 409 CONFLICT Error
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends ResponseStatusException {
    public ConflictException(){
        super(HttpStatus.CONFLICT);
    }
    public ConflictException(String message){
        super(HttpStatus.CONFLICT, message);
    }
}

