package com.warehouse.customexception;

public class InvalidOtpFoundException extends RuntimeException {

    public InvalidOtpFoundException() {
    }

    public InvalidOtpFoundException(String message) {
        super(message);
    }
}
