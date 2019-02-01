package com.haulmont.testtask.service;

import com.haulmont.testtask.database.impl.HSQLDBDaoManager;
import com.haulmont.testtask.database.impl.HSQLDBGroupDao;
import com.haulmont.testtask.database.impl.HSQLDBStudentDao;
import com.haulmont.testtask.exception.service.ServiceException;
import com.haulmont.testtask.container.GroupContainer;
import com.haulmont.testtask.container.StudentContainer;
import com.haulmont.testtask.model.entity.Group;
import com.haulmont.testtask.model.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Service {
    private GroupContainer groupContainer;
    private StudentContainer studentContainer;
    private HSQLDBDaoManager hsqldbDaoManager;
    private HSQLDBGroupDao hsqldbGroupDao;
    private HSQLDBStudentDao hsqldbStudentDao;
    private static Service instance;

    private Service() throws ServiceException {
        try {
            hsqldbDaoManager = HSQLDBDaoManager.getInstance();
            hsqldbGroupDao = (HSQLDBGroupDao) hsqldbDaoManager.getGroupDao();
            hsqldbStudentDao = (HSQLDBStudentDao) hsqldbDaoManager.getStudentDao();
            createGroupContainer();
            createStudentContainer();
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private void createGroupContainer() throws ServiceException {
        try {
            groupContainer = new GroupContainer();
            for (Group group : hsqldbGroupDao.getAll()) {
                groupContainer.addGroup(group);
            }
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private void createStudentContainer() throws ServiceException {
        try {
            studentContainer = new StudentContainer();
            for (Student student : hsqldbStudentDao.getAll()) {
                studentContainer.addStudent(student);
            }
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static synchronized Service getInstance() throws ServiceException {
        if (instance == null)
            instance = new Service();
        return instance;
    }

    public boolean insertStudent(Student clazz) throws ServiceException {
        try {
            if (hsqldbStudentDao.getStudentId(clazz) == null) {
                clazz.setId(hsqldbStudentDao.insert(clazz));
                studentContainer.addStudent(clazz);
                return true;
            } else return false;
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void deleteStudent(Long id) throws ServiceException {
        try {
            hsqldbStudentDao.delete(id);
            studentContainer.removeStudent(id);
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Student> getStudents() {
        return studentContainer.getStudents();
    }

    public List<Student> getStudents(String filterLastName, String filterNumber) {
        List<Student> list = new ArrayList<>();
        for (Student student : studentContainer.getStudents()) {
            boolean passesFilter = ((filterLastName == null || filterLastName.isEmpty()) ||
                    student.getLastName().toLowerCase().contains(filterLastName.toLowerCase())) &&
                            (getGroup(student.getGroupId()).getNumber().toString().contains(filterNumber.toLowerCase()) || filterNumber == null || filterNumber.isEmpty());
            if (passesFilter)
                list.add(student);
        }
        Collections.sort(list, (s1, s2) -> (int) (s2.getId() - s1.getId()));
        return list;
    }

    public Student getStudent(Long id) {
        return studentContainer.getStudent(id);
    }

    public void updateStudent(Student clazz) throws ServiceException {
        try {
            hsqldbStudentDao.update(clazz);
            studentContainer.updateStudent(clazz);
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean insertGroup(Group clazz) throws ServiceException {
        try {
            if (hsqldbGroupDao.getGroupId(clazz) == null) {
                clazz.setId(hsqldbGroupDao.insert(clazz));
                groupContainer.addGroup(clazz);
                return true;
            } else return false;
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean deleteGroup(Long id) throws ServiceException {
        try {
            if (studentContainer.getStudentsByGroup(id).size() == 0) {
                hsqldbGroupDao.delete(id);
                groupContainer.removeGroup(id);
                return true;
            }
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }
        return false;
    }

    public List<Group> getGroups() {
        return groupContainer.getGroups();
    }

    public Group getGroup(Long id) {
        return groupContainer.getGroup(id);
    }

    public void updateGroup(Group clazz) throws ServiceException {
        try {
            hsqldbGroupDao.update(clazz);
            groupContainer.updateGroup(clazz);
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
