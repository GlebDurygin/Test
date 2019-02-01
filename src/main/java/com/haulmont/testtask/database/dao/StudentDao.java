package com.haulmont.testtask.database.dao;

import com.haulmont.testtask.model.entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDao {

    public Long insert(Student clazz) throws SQLException;

    public Student getById(Long id) throws SQLException;

    public void delete(Long id) throws SQLException;

    public void update(Student clazz) throws SQLException;

    public List<Student> getAll() throws SQLException;
}
