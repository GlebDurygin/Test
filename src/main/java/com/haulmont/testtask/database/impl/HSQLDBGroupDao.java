package com.haulmont.testtask.database.impl;

import com.haulmont.testtask.database.dao.GroupDao;
import com.haulmont.testtask.exception.database.impl.DataBaseConnectionException;
import com.haulmont.testtask.exception.database.impl.DriverNotFoundException;
import com.haulmont.testtask.model.entity.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.haulmont.testtask.database.impl.HSQLDBConstants.*;

public class HSQLDBGroupDao implements GroupDao {
    private Connection connection;

    private Logger logger = Logger.getLogger(HSQLDBGroupDao.class.getName());

    public HSQLDBGroupDao() {
        try {
            connection = HSQLDBConnection.getConnection();
        } catch (DriverNotFoundException | DataBaseConnectionException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public Long insert(Group group) throws SQLException {
        try {
            String sql = "INSERT INTO " + TABLE_GROUP + " (" + TABLE_GROUP_NUMBER + ", " + TABLE_GROUP_FACULTY + ") values (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, group.getNumber());
            preparedStatement.setString(2, group.getFaculty());
            preparedStatement.executeUpdate();
            return getGroupId(group);
        } catch (SQLException e) {
            logger.severe(HSQLDBErrorConstants.INSERT_ERROR + e.getMessage());
            throw new SQLException(HSQLDBErrorConstants.INSERT_ERROR + e.getMessage());
        }
    }

    public Group getById(Long id) throws SQLException {
        Group group = new Group();
        String sql = "SELECT * FROM " + TABLE_GROUP + " WHERE " + TABLE_GROUP_ID + " = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            group.setId(resultSet.getLong(TABLE_GROUP_ID));
            group.setNumber(resultSet.getInt(TABLE_GROUP_NUMBER));
            group.setFaculty(resultSet.getString(TABLE_GROUP_FACULTY));
            return group;
        } catch (SQLException e) {
            logger.severe(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
            throw new SQLException(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }

    public Long getGroupId(Group group) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_GROUP + " WHERE " + TABLE_GROUP_NUMBER + " = ? AND " + TABLE_GROUP_FACULTY + " = ?;";
        try {
            PreparedStatement preparedStatementSelect = connection.prepareStatement(sql);
            preparedStatementSelect.setInt(1, group.getNumber());
            preparedStatementSelect.setString(2, group.getFaculty());
            ResultSet resultSet = preparedStatementSelect.executeQuery();
            if (resultSet.next()) return resultSet.getLong(TABLE_GROUP_ID);
            else return null;
        } catch (SQLException e) {
            logger.severe(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
            throw new SQLException(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM " + TABLE_GROUP + " WHERE " + TABLE_GROUP_ID + " = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe(HSQLDBErrorConstants.DELETE_ERROR + e.getMessage());
            throw new SQLException(HSQLDBErrorConstants.DELETE_ERROR + e.getMessage());
        }
    }

    @Override
    public void update(Group group) throws SQLException {
        String sql = "UPDATE " + TABLE_GROUP + " SET " + TABLE_GROUP_NUMBER + " = ?, " + TABLE_GROUP_FACULTY + " = ? WHERE " + TABLE_GROUP_ID + " = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, group.getNumber());
            preparedStatement.setString(2, group.getFaculty());
            preparedStatement.setLong(3, group.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe(HSQLDBErrorConstants.UPDATE_ERROR + e.getMessage());
            throw new SQLException(HSQLDBErrorConstants.UPDATE_ERROR + e.getMessage());
        }
    }

    @Override
    public List<Group> getAll() throws SQLException {
        List<Group> groups = new ArrayList<Group>();
        String sql = "SELECT * FROM " + TABLE_GROUP + ";";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group(resultSet.getLong(TABLE_GROUP_ID),
                        resultSet.getInt(TABLE_GROUP_NUMBER),
                        resultSet.getString(TABLE_GROUP_FACULTY));
                groups.add(group);
            }
            return groups;
        } catch (SQLException e) {
            logger.severe(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
            throw new SQLException(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }
}
