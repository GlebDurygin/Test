package com.haulmont.testtask.exception.dao;

public class EmptyDaoException extends Exception{
    public EmptyDaoException() {
        super();
    }

    public EmptyDaoException(String message) {
        super(message);
    }
}
