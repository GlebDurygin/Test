package com.haulmont.testtask.service;

import com.haulmont.testtask.database.impl.HSQLDBDaoManager;
import com.haulmont.testtask.database.impl.HSQLDBGroupDao;
import com.haulmont.testtask.database.impl.HSQLDBStudentDao;
import com.haulmont.testtask.model.entity.Group;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupService {
    private HSQLDBDaoManager daoManager;
    private HSQLDBGroupDao groupDao;
    private HSQLDBStudentDao studentDao;

    private static GroupService instance;

    private GroupService() {
        daoManager = HSQLDBDaoManager.getInstance();
        groupDao = (HSQLDBGroupDao) daoManager.getGroupDao();
        studentDao = (HSQLDBStudentDao) daoManager.getStudentDao();
    }

    public static synchronized GroupService getInstance() {
        if (instance == null) instance = new GroupService();
        return instance;
    }

    public boolean insertGroup(Group group) {
        try {
            if (groupDao.getGroupId(group) == null) {
                groupDao.insert(group);
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public boolean deleteGroup(Long id) {
        try {
            if (studentDao.getStudentsByGroup(id).size() == 0) {
                groupDao.delete(id);
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public boolean updateGroup(Group group) {
        try {
            groupDao.update(group);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Group getGroup(Long id) {
        try {
            return groupDao.getById(id);
        } catch (SQLException e) {
            return new Group();
        }
    }

    public List<Group> getGroups() {
        try {
            return groupDao.getAll();
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
