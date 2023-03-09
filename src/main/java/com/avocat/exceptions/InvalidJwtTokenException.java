package com.avocat.exceptions;

public class InvalidJwtTokenException extends RuntimeException {

    public InvalidJwtTokenException(String msg) {
        super(msg);
    }
}
