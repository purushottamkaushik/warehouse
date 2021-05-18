package com.warehouse.customexception;

public class ShipmentTypeNotFoundException extends RuntimeException {

    public ShipmentTypeNotFoundException() {
    }

    public ShipmentTypeNotFoundException(String message) {
        super(message);
    }
}
