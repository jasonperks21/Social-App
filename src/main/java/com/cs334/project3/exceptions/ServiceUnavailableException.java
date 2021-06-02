package com.cs334.project3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

// 503 SERVICE UNAVAILABLE Error
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnavailableException extends ResponseStatusException {
    public ServiceUnavailableException(){
        super(HttpStatus.SERVICE_UNAVAILABLE);
    }
    public ServiceUnavailableException(String message){
        super(HttpStatus.SERVICE_UNAVAILABLE, message);
    }
}
