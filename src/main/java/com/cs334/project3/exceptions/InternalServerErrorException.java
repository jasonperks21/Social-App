package com.cs334.project3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

// 500 INTERNAL SERVER ERROR Error
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends ResponseStatusException {
    public InternalServerErrorException(){
        super(HttpStatus.METHOD_NOT_ALLOWED);
    }
    public InternalServerErrorException(String message){
        super(HttpStatus.METHOD_NOT_ALLOWED, message);
    }
}
