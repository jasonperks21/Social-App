package com.cs334.project3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

// 418 I AM A TEAPOT Error
@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
public class IAmATeapotException extends ResponseStatusException {
    public IAmATeapotException(){
        super(HttpStatus.I_AM_A_TEAPOT);
    }
    public IAmATeapotException(String message){
        super(HttpStatus.I_AM_A_TEAPOT, message);
    }
}
