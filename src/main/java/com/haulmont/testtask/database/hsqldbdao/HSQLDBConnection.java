package com.haulmont.testtask.database.hsqldbdao;

import com.haulmont.testtask.exception.database.hsqldbdao.DataBaseConnectionException;
import com.haulmont.testtask.exception.database.hsqldbdao.NotFoundDriverException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

import static com.haulmont.testtask.database.hsqldbdao.HSQLDBConstants.*;

public class HSQLDBConnection {

    private static Connection connection;

    private static void buildConnection() throws DataBaseConnectionException, NotFoundDriverException {
        try {
            Class.forName(HSQLDB_DRIVER);
            connection = DriverManager.getConnection(HSQLDB_URL, HSQLDB_USERNAME, HSQLDB_PASSWORD);
            initDatabase();
            //return connect;
        } catch (ClassNotFoundException e) {
            throw new NotFoundDriverException(HSQLDBErrorConstants.DRIVER_ERROR);
        } catch (SQLException e) {
            throw new DataBaseConnectionException(HSQLDBErrorConstants.CONNECTION_ERROR);
        }
    }

    public static Connection getConnection() throws DataBaseConnectionException, NotFoundDriverException {
        if (connection == null) buildConnection();
        return connection;
    }

    public static void closeConnection() throws DataBaseConnectionException {
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                statement.execute("SHUTDOWN");
            }
        } catch (SQLException e) {
            throw new DataBaseConnectionException(HSQLDBErrorConstants.CLOSE_DB_ERROR);
        }
    }

    private static void initDatabase() throws SQLException, DataBaseConnectionException {
        if (!tableExists(TABLE_GROUP) || !tableExists(TABLE_STUDENT))
            executeSqlStartScript(HSQLDB_SCRIPT_PATH);
    }

    private static boolean tableExists(String tableName) throws SQLException {
        boolean isExists = false;
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String table_name = rs.getString("TABLE_NAME");
                if (table_name != null && table_name.equals(tableName)) {
                    isExists = true;
                    break;
                }
            }
        }
        return isExists;
    }

    private static void executeSqlStartScript(String path) throws DataBaseConnectionException {
        String delimiter = ";";
        Scanner scanner;
        try {
            scanner = new Scanner(new FileInputStream(path)).useDelimiter(delimiter);
            Statement statement = null;
            while (scanner.hasNext()) {
                String scriptStatement = scanner.next() + delimiter;
                try {
                    statement = connection.createStatement();
                    statement.execute(scriptStatement);
                } catch (SQLException e) {
                    throw  new DataBaseConnectionException(HSQLDBErrorConstants.STATEMENT_ERROR);
                } finally {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (SQLException e) {
                            throw  new DataBaseConnectionException(HSQLDBErrorConstants.STATEMENT_ERROR);
                        }
                    }
                    statement = null;
                }
            }
            scanner.close();
        } catch (FileNotFoundException | DataBaseConnectionException e) {
            throw  new DataBaseConnectionException(HSQLDBErrorConstants.SCRIPT_ERROR);
        }
    }
}
