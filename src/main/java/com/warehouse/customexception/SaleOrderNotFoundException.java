package com.warehouse.customexception;

public class SaleOrderNotFoundException extends RuntimeException {
    public SaleOrderNotFoundException(String s) {
        super(s);
    }

    public SaleOrderNotFoundException() {
    }
}
