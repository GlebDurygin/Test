package com.haulmont.testtask.database.dao;

import com.haulmont.testtask.model.entity.Group;

import java.sql.SQLException;
import java.util.List;

public interface GroupDao {
    public Long insert(Group clazz) throws SQLException;

    public Group getById(Long id) throws SQLException;

    public void delete(Long id) throws SQLException;

    public void update(Group clazz) throws SQLException;

    public List<Group> getAll() throws SQLException;

}