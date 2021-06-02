package com.cs334.project3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

// 403 FORBIDDEN Error
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends ResponseStatusException {
    public ForbiddenException(){
        super(HttpStatus.FORBIDDEN);
    }
    public ForbiddenException(String message){
        super(HttpStatus.FORBIDDEN, message);
    }
}