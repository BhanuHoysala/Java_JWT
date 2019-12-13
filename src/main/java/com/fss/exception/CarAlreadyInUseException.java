package com.fss.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Car is already in use.")
public class CarAlreadyInUseException extends Exception
{


    public CarAlreadyInUseException(String message) {
        super(message);
    }
}
