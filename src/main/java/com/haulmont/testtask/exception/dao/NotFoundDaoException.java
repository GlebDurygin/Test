package com.haulmont.testtask.exception.dao;

public class NotFoundDaoException extends Exception {
    public NotFoundDaoException() {
        super();
    }

    public NotFoundDaoException(String message) {
        super(message);
    }
}
