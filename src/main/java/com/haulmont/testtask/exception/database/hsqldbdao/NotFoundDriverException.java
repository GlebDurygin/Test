package com.haulmont.testtask.exception.database.hsqldbdao;

public class NotFoundDriverException extends Exception {
    public NotFoundDriverException() {
        super();
    }

    public NotFoundDriverException(String message) {
        super(message);
    }
}
