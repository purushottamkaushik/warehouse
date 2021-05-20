package com.warehouse.customexception;

public class OrderMethodNotFoundException extends RuntimeException{

    public OrderMethodNotFoundException() {
    }

    public OrderMethodNotFoundException(String message) {
        super(message);
    }
}
