package com.haulmont.testtask.database.hsqldbdao;

import com.haulmont.testtask.database.dao.StudentDao;
import com.haulmont.testtask.exception.database.hsqldbdao.DataBaseConnectionException;
import com.haulmont.testtask.exception.database.hsqldbdao.NotFoundDriverException;
import com.haulmont.testtask.model.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.haulmont.testtask.database.hsqldbdao.HSQLDBConstants.*;

public class HSQLDBStudentDao implements StudentDao {
    private Connection connection;

    @Override
    public Long insert(Student clazz) throws SQLException {
        String sql = "INSERT INTO " + TABLE_STUDENT + " (" + TABLE_STUDENT_FIRST_NAME + ", "
                + TABLE_STUDENT_LAST_NAME + ", "
                + TABLE_STUDENT_MIDDLE_NAME + ", "
                + TABLE_STUDENT_BIRTHDATE + ", "
                + TABLE_STUDENT_GROUP_ID + ") values (?,?,?,?,?);";
        try {
            connection = HSQLDBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clazz.getFirstName());
            preparedStatement.setString(2, clazz.getLastName());
            preparedStatement.setString(3, clazz.getMiddleName());
            preparedStatement.setDate(4, clazz.getBirthDate());
            preparedStatement.setLong(5, clazz.getGroupId());
            preparedStatement.executeUpdate();
            return getStudentId(clazz);
        } catch (SQLException | NotFoundDriverException | DataBaseConnectionException e) {
            throw new SQLException(HSQLDBErrorConstants.INSERT_ERROR + e.getMessage());
        }
    }

    public Long getStudentId(Student clazz) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + TABLE_STUDENT_FIRST_NAME + " = ? AND " +
                TABLE_STUDENT_LAST_NAME + " = ? AND " + TABLE_STUDENT_MIDDLE_NAME + " = ? AND " +
                TABLE_STUDENT_BIRTHDATE + " = ? AND " + TABLE_STUDENT_GROUP_ID + " = ?;";
        try {
            connection = HSQLDBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clazz.getFirstName());
            preparedStatement.setString(2, clazz.getLastName());
            preparedStatement.setString(3, clazz.getMiddleName());
            preparedStatement.setDate(4, clazz.getBirthDate());
            preparedStatement.setLong(5, clazz.getGroupId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getLong(TABLE_STUDENT_ID);
            else return null;
        } catch (SQLException | NotFoundDriverException | DataBaseConnectionException e) {
            throw new SQLException(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }

    @Override
    public Student getById(Long id) throws SQLException {
        Student student = new Student();
        String sql = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + TABLE_STUDENT_ID + " = ?;";
        try {
            connection = HSQLDBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            student.setFirstName(resultSet.getString(TABLE_STUDENT_FIRST_NAME));
            student.setLastName(resultSet.getString(TABLE_STUDENT_LAST_NAME));
            student.setMiddleName(resultSet.getString(TABLE_STUDENT_MIDDLE_NAME));
            student.setBirthDate(resultSet.getDate(TABLE_STUDENT_BIRTHDATE));
            student.setGroupId(resultSet.getLong(TABLE_STUDENT_GROUP_ID));
            return student;
        } catch (SQLException | NotFoundDriverException | DataBaseConnectionException e) {
            throw new SQLException(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM " + TABLE_STUDENT + " WHERE " + TABLE_STUDENT_ID + " = ?;";
        try {
            connection = HSQLDBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | NotFoundDriverException | DataBaseConnectionException e) {
            throw new SQLException(HSQLDBErrorConstants.DELETE_ERROR + e.getMessage());
        }
    }

    @Override
    public void update(Student clazz) throws SQLException {
        String sql = "UPDATE " + TABLE_STUDENT + " SET " + TABLE_STUDENT_FIRST_NAME + " = ?, "
                + TABLE_STUDENT_LAST_NAME + " = ?, "
                + TABLE_STUDENT_MIDDLE_NAME + " = ?, "
                + TABLE_STUDENT_BIRTHDATE + " = ?, "
                + TABLE_STUDENT_GROUP_ID + " = ? WHERE " + TABLE_STUDENT_ID + " = ?;";
        try {
            connection = HSQLDBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clazz.getFirstName());
            preparedStatement.setString(2, clazz.getLastName());
            preparedStatement.setString(3, clazz.getMiddleName());
            preparedStatement.setDate(4, clazz.getBirthDate());
            preparedStatement.setLong(5, clazz.getGroupId());
            preparedStatement.setLong(6, clazz.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | NotFoundDriverException | DataBaseConnectionException e) {
            throw new SQLException(HSQLDBErrorConstants.UPDATE_ERROR + e.getMessage());
        }
    }

    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_STUDENT + ";";
        try {
            connection = HSQLDBConnection.getConnection();
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
        } catch (SQLException | NotFoundDriverException | DataBaseConnectionException e) {
            throw new SQLException(HSQLDBErrorConstants.SELECT_ERROR + e.getMessage());
        }
    }
}
