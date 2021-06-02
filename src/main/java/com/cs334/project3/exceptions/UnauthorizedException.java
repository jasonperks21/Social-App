package com.cs334.project3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

// 401 UNAUTHORIZED Error
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends ResponseStatusException {
    public UnauthorizedException(){
        super(HttpStatus.UNAUTHORIZED);
    }
    public UnauthorizedException(String message){
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
