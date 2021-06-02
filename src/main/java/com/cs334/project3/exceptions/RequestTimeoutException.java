package com.cs334.project3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

// 408 REQUEST TIMEOUT Error
@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
public class RequestTimeoutException extends ResponseStatusException {
    public RequestTimeoutException(){
        super(HttpStatus.REQUEST_TIMEOUT);
    }
    public RequestTimeoutException(String message){
        super(HttpStatus.REQUEST_TIMEOUT, message);
    }
}