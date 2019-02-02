package com.haulmont.testtask.database.dao;

import com.haulmont.testtask.exception.database.impl.DaoException;
import com.haulmont.testtask.model.entity.Student;

import java.util.List;

public interface StudentDao {

    Long insert(Student student) throws DaoException;

    void delete(Long id) throws DaoException;

    void update(Student student) throws DaoException;

    List<Student> getAll() throws DaoException;
}
