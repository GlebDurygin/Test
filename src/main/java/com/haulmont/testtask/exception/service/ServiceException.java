package com.haulmont.testtask.exception.service;

public class ServiceException extends RuntimeException {
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }
}
