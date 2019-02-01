package com.haulmont.testtask.database.hsqldbdao;

public class HSQLDBConstants {
    public static final String
            HSQLDB_DRIVER = "org.hsqldb.jdbc.JDBCDriver",
            //HSQLDB_URL = "jdbc:hsqldb:file:C:/Users/Gleb Durygin/Documents/IdeaProjects/Test-Task/database/hsqldb/",
            HSQLDB_URL = "jdbc:hsqldb:file:~/data/hsqldb/",
            HSQLDB_USERNAME = "SA",
            HSQLDB_PASSWORD = "",
            HSQLDB_SCRIPT_PATH = "data/scripts/createTable",


            TABLE_GROUP = "T_GROUP",
            TABLE_GROUP_ID = "ID",
            TABLE_GROUP_NUMBER = "NUMBER",
            TABLE_GROUP_FACULTY = "FACULTY",
            TABLE_STUDENT = "T_STUDENT",
            TABLE_STUDENT_ID = "ID",
            TABLE_STUDENT_FIRST_NAME = "FIRST_NAME",
            TABLE_STUDENT_LAST_NAME = "LAST_NAME",
            TABLE_STUDENT_MIDDLE_NAME = "MIDDLE_NAME",
            TABLE_STUDENT_BIRTHDATE = "BIRTHDATE",
            TABLE_STUDENT_GROUP_ID = "GROUP_ID";
}
