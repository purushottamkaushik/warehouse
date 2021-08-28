package com.warehouse.customexception;

public class ShippingNotFoundException extends RuntimeException {
    public ShippingNotFoundException(String s) {
        super(s);
    }

    public ShippingNotFoundException() {
    }
}
