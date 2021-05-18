package com.warehouse.customexception;

public class UomNotFoundException extends RuntimeException {
    public UomNotFoundException() {
    }

    public UomNotFoundException(String message) {
        super(message);
    }
}
