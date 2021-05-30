package com.warehouse.customexception;

public class PartNotFoundException extends RuntimeException {
    public PartNotFoundException() {
    }

    public PartNotFoundException(String message) {
        super(message);
    }
}
