package com.haulmont.testtask.database.dao;

import com.haulmont.testtask.model.entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDao {

    Long insert(Student student) throws SQLException;

    void delete(Long id) throws SQLException;

    void update(Student student) throws SQLException;

    List<Student> getAll() throws SQLException;
}
