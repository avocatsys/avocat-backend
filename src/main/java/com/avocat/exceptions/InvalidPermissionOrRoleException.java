package com.avocat.exceptions;

public class InvalidPermissionOrRoleException extends RuntimeException {

    public InvalidPermissionOrRoleException(String msg) {
        super(msg);
    }
}
