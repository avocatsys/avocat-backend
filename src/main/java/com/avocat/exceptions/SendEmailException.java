package com.avocat.exceptions;

public class SendEmailException extends RuntimeException {

    public SendEmailException(String msg) {
        super(msg);
    }
}
