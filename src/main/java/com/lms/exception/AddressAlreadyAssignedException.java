package com.lms.exception;

public class AddressAlreadyAssignedException extends RuntimeException {

    public AddressAlreadyAssignedException(String message) {
        super(message);
    }
}