package com.haulmont.testtask.database.hsqldbdao;

import com.haulmont.testtask.database.dao.DaoManager;
import com.haulmont.testtask.database.dao.GroupDao;
import com.haulmont.testtask.database.dao.StudentDao;

public class HSQLDBDaoManager implements DaoManager {
    private static HSQLDBDaoManager instance;
    private static GroupDao groupDao;
    private static StudentDao studentDao;

    public HSQLDBDaoManager() {

    }

    public static synchronized HSQLDBDaoManager getInstance() {
        if(instance == null) {
            instance = new HSQLDBDaoManager();
        }
        return instance;
    }
    @Override
    public GroupDao getGroupDao() {
        if (groupDao == null) {
            groupDao = new HSQLDBGroupDao();
        }
        return groupDao;
    }

    @Override
    public StudentDao getStudentDao() {
        if (studentDao == null) {
            studentDao = new HSQLDBStudentDao();
        }
        return studentDao;
    }
}
