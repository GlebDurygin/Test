package com.haulmont.testtask.database.dao;

import com.haulmont.testtask.exception.database.impl.DaoException;

public interface DaoManager {

    GroupDao getGroupDao() throws DaoException;

    StudentDao getStudentDao() throws DaoException;
}
