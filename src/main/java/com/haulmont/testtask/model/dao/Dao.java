package com.haulmont.testtask.model.dao;

import com.haulmont.testtask.exception.dao.DaoException;
import com.haulmont.testtask.exception.dao.EmptyDaoException;
import com.haulmont.testtask.exception.dao.NotFoundDaoException;
import com.haulmont.testtask.model.entity.Entity;

import java.util.List;

public interface Dao<T extends Entity> {
    public void insert(T clazz) throws DaoException;

    public T getById(Long id) throws NotFoundDaoException;

    public void remove(Long id) throws NotFoundDaoException;

    public void update(Long id, T clazz) throws NotFoundDaoException;

    public List<T> getAll() throws EmptyDaoException;

}
