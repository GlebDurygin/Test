package com.haulmont.testtask.service;

import com.haulmont.testtask.database.impl.HSQLDBDaoManager;
import com.haulmont.testtask.database.impl.HSQLDBGroupDao;
import com.haulmont.testtask.database.impl.HSQLDBStudentDao;
import com.haulmont.testtask.exception.database.impl.DaoException;
import com.haulmont.testtask.exception.service.ServiceException;
import com.haulmont.testtask.model.entity.Student;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StudentService {
    private HSQLDBDaoManager daoManager;
    private HSQLDBGroupDao groupDao;
    private HSQLDBStudentDao studentDao;

    private static StudentService instance;

    private Logger logger = Logger.getLogger(StudentService.class.getName());

    private StudentService() {
        try {
            daoManager = HSQLDBDaoManager.getInstance();
            groupDao = (HSQLDBGroupDao) daoManager.getGroupDao();
            studentDao = (HSQLDBStudentDao) daoManager.getStudentDao();
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public static synchronized StudentService getInstance() {
        if (instance == null) instance = new StudentService();
        return instance;
    }

    public boolean insertStudent(Student student) {
        try {
            if (studentDao.getStudentId(student) == null) {
                studentDao.insert(student);
                return true;
            }
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return false;
    }

    public boolean deleteStudent(Long id) {
        try {
            studentDao.delete(id);
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    public boolean updateStudent(Student student) {
        try {
            studentDao.update(student);
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    public List<Student> getStudents(String filterLastName, String filterNumber) {
        try {
            return studentDao.getAll().stream().filter(student -> {
                try {
                    return ((filterLastName == null || filterLastName.isEmpty()) ||
                            student.getLastName().toLowerCase().contains(filterLastName.toLowerCase())) &&
                            (groupDao.getById(student.getGroupId()).getNumber().toString().contains(filterNumber.toLowerCase()) || filterNumber == null || filterNumber.isEmpty());
                } catch (DaoException e) {
                    logger.severe(e.getMessage());
                    throw new ServiceException(e.getMessage());
                }
            }).collect(Collectors.toList());
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
