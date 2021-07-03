package com.warehouse.customexception;

public class GrnNotFoundException extends RuntimeException {
    public GrnNotFoundException() {
        System.out.println("GRn not found exception constructor called");
    }

    public GrnNotFoundException(String message) {
        super(message);
    }
}
