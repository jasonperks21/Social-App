package com.cs334.project3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

// 400 BAD REQUEST Error
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends ResponseStatusException {
    public BadRequestException(){
        super(HttpStatus.BAD_REQUEST);
    }
    public BadRequestException(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}
