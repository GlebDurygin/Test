package com.haulmont.testtask.model.entity;

import java.sql.Date;

public class Student implements Entity {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private Long groupId;

    public Student() {

    }

    public Student(Long id, String firstName, String LastName, String MiddleName, Date birthDate, Long groupId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = LastName;
        this.middleName = MiddleName;
        this.birthDate = birthDate;
        this.groupId = groupId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}
