package com.haulmont.testtask.container;

import com.haulmont.testtask.model.entity.Student;

import java.util.*;
import java.util.stream.Collectors;

public class StudentContainer {
    private Map<Long, Student> students;

    public StudentContainer() {
        students = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    public void removeStudent(Long id) {
        students.remove(id);
    }

    public Student getStudent(Long id) {
        return students.get(id);
    }

    public void updateStudent(Student student) {
        students.replace(student.getId(), students.get(student.getId()), student);
    }

    public List<Student> getStudents() {
        List<Student> list = new ArrayList<>(students.values());
        return Collections.unmodifiableList(list);
    }

    public List<Student> getStudentsByGroup(Long id) {
        return students.values().stream().filter(student -> student.getGroupId().equals(id)).collect(Collectors.toList());
    }
}
