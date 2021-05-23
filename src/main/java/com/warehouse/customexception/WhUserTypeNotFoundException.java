package com.warehouse.customexception;

public class WhUserTypeNotFoundException extends RuntimeException {
    public WhUserTypeNotFoundException() {
        super();
    }

    public WhUserTypeNotFoundException(String message) {
        super(message);
    }
}
