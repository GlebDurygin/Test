package com.haulmont.testtask.exception.database.impl;

public class DriverNotFoundException extends Exception {
    public DriverNotFoundException() {
        super();
    }

    public DriverNotFoundException(String message) {
        super(message);
    }
}
