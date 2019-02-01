package com.haulmont.testtask.exception.database.hsqldbdao;

public class DataBaseConnectionException extends Exception {
    public DataBaseConnectionException() {
        super();
    }

    public DataBaseConnectionException(String message) {
        super(message);
    }
}
