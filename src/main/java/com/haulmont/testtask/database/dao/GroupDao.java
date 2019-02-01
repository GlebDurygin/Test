package com.haulmont.testtask.database.dao;

import com.haulmont.testtask.model.entity.Group;

import java.sql.SQLException;
import java.util.List;

public interface GroupDao {

    Long insert(Group group) throws SQLException;

    void delete(Long id) throws SQLException;

    void update(Group group) throws SQLException;

    List<Group> getAll() throws SQLException;

}