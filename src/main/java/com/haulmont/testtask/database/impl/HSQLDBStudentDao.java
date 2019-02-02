package com.haulmont.testtask.database.impl;

import com.haulmont.testtask.database.dao.StudentDao;
import com.haulmont.testtask.exception.database.impl.DaoException;
import com.haulmont.testtask.exception.database.impl.DataBaseConnectionException;
import com.haulmont.testtask.exception.database.impl.DriverNotFoundException;
import com.haulmont.testtask.model.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.haulmont.testtask.database.impl.HSQLDBConstants.*;

public class HSQLDBStudentDao implements StudentDao {
    private Connection connection;

    public HSQLDBStudentDao() throws DaoException {
        try {
            connection = HSQLDBConnection.getConnection();
        } catch (DriverNotFoundException | DataBaseConnectionException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Long insert(Student student) throws DaoException {
        String sql = "INSERT INTO " + TABLE_STUDENT + " (" + TABLE_STUDENT_FIRST_NAME + ", "
                + TABLE_STUDENT_LAST_NAME + ", "
                + TABLE_STUDENT_MIDDLE_NAME + ", "
                + TABLE_STUDENT_BIRTHDATE + ", "
                + TABLE_STUDENT_GROUP_ID + ") values (?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getMiddleName());
            preparedStatement.setDate(4, student.getBirthDate());
            preparedStatement.setLong(5, student.getGroupId());
            preparedStatement.executeUpdate();
            return getStudentId(student);
        } catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.INSERT_ERROR + e.getMessage());
        }
    }

    public Long getStudentId(Student student) throws DaoException {
        String sql = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + TABLE_STUDENT_FIRST_NAME + " = ? AND " +
                TABLE_STUDENT_LAST_NAME + " = ? AND " + TABLE_STUDENT_MIDDLE_NAME + " = ? AND " +
                TABLE_STUDENT_BIRTHDATE + " = ? AND " + TABLE_STUDENT_GROUP_ID + " = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getMiddleName());
            preparedStatement.setDate(4, student.getBirthDate());
            preparedStatement.setLong(5, student.getGroupId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return resultSet.getLong(TABLE_STUDENT_ID);
            else return null;
        } catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "DELETE FROM " + TABLE_STUDENT + " WHERE " + TABLE_STUDENT_ID + " = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.DELETE_ERROR + e.getMessage());
        }
    }

    @Override
    public void update(Student student) throws DaoException {
        String sql = "UPDATE " + TABLE_STUDENT + " SET " + TABLE_STUDENT_FIRST_NAME + " = ?, "
                + TABLE_STUDENT_LAST_NAME + " = ?, "
                + TABLE_STUDENT_MIDDLE_NAME + " = ?, "
                + TABLE_STUDENT_BIRTHDATE + " = ?, "
                + TABLE_STUDENT_GROUP_ID + " = ? WHERE " + TABLE_STUDENT_ID + " = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getMiddleName());
            preparedStatement.setDate(4, student.getBirthDate());
            preparedStatement.setLong(5, student.getGroupId());
            preparedStatement.setLong(6, student.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.UPDATE_ERROR + e.getMessage());
        }
    }

    @Override
    public List<Student> getAll() throws DaoException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_STUDENT + ";";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student(resultSet.getLong(TABLE_STUDENT_ID),
                        resultSet.getString(TABLE_STUDENT_FIRST_NAME),
                        resultSet.getString(TABLE_STUDENT_LAST_NAME),
                        resultSet.getString(TABLE_STUDENT_MIDDLE_NAME),
                        resultSet.getDate(TABLE_STUDENT_BIRTHDATE),
                        resultSet.getLong(TABLE_STUDENT_GROUP_ID));
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }

    public List<Student> getStudentsByGroup(Long groupId) throws DaoException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + TABLE_STUDENT_GROUP_ID + " = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student(resultSet.getLong(TABLE_STUDENT_ID),
                        resultSet.getString(TABLE_STUDENT_FIRST_NAME),
                        resultSet.getString(TABLE_STUDENT_LAST_NAME),
                        resultSet.getString(TABLE_STUDENT_MIDDLE_NAME),
                        resultSet.getDate(TABLE_STUDENT_BIRTHDATE),
                        resultSet.getLong(TABLE_STUDENT_GROUP_ID));
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            throw new DaoException(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }
}
