package com.haulmont.testtask.database.dao;

import com.haulmont.testtask.exception.database.impl.DaoException;
import com.haulmont.testtask.model.entity.Group;

import java.util.List;

public interface GroupDao {

    Long insert(Group group) throws DaoException;

    void delete(Long id) throws DaoException;

    void update(Group group) throws DaoException;

    List<Group> getAll() throws DaoException;

}