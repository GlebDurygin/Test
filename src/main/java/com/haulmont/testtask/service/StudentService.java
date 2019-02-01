package com.haulmont.testtask.service;

import com.haulmont.testtask.database.impl.HSQLDBDaoManager;
import com.haulmont.testtask.database.impl.HSQLDBGroupDao;
import com.haulmont.testtask.database.impl.HSQLDBStudentDao;
import com.haulmont.testtask.model.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private HSQLDBDaoManager daoManager;
    private HSQLDBGroupDao groupDao;
    private HSQLDBStudentDao studentDao;


    private static StudentService instance;

    private StudentService() {
        daoManager = HSQLDBDaoManager.getInstance();
        groupDao = (HSQLDBGroupDao) daoManager.getGroupDao();
        studentDao = (HSQLDBStudentDao) daoManager.getStudentDao();
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
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public boolean deleteStudent(Long id) {
        try {
            studentDao.delete(id);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean updateStudent(Student student) {
        try {
            studentDao.update(student);
        } catch (SQLException e) {
            return false;
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
                } catch (SQLException e) {
                    return false;
                }
            }).collect(Collectors.toList());
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
