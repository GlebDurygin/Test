package com.haulmont.testtask.database.impl;

import com.haulmont.testtask.database.dao.DaoManager;
import com.haulmont.testtask.database.dao.GroupDao;
import com.haulmont.testtask.database.dao.StudentDao;
import com.haulmont.testtask.exception.database.impl.DaoException;

public class HSQLDBDaoManager implements DaoManager {
    private static HSQLDBDaoManager instance;
    private GroupDao groupDao;
    private StudentDao studentDao;

    public HSQLDBDaoManager() {

    }

    public static synchronized HSQLDBDaoManager getInstance() {
        if (instance == null) {
            instance = new HSQLDBDaoManager();
        }
        return instance;
    }

    @Override
    public GroupDao getGroupDao() throws DaoException {
        if (groupDao == null) {
            groupDao = new HSQLDBGroupDao();
        }
        return groupDao;
    }

    @Override
    public StudentDao getStudentDao() throws DaoException {
        if (studentDao == null) {
            studentDao = new HSQLDBStudentDao();
        }
        return studentDao;
    }
}
