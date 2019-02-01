package com.haulmont.testtask.model.container;

import com.haulmont.testtask.model.entity.Student;

import java.util.*;

public class StudentContainer {
    private Map<Long, Student> students;

    public StudentContainer() {
        students = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.getId(),student);
    }

    public void removeStudent(Student student) {
        students.remove(student.getId());
    }

    public void removeStudent(Long id) {
        students.remove(id);
    }

    public Student getStudent(Long id) {
        return students.get(id);
    }

    public void updateStudent(Student student) {
        students.replace(student.getId(),students.get(student.getId()),student);
    }

    public List<Student> getStudents() {
        List<Student> list = new ArrayList<>(students.values());
        return Collections.unmodifiableList(list);
    }

    public List<Student> studentsInGroup(Long id){
        List<Student> list = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getGroupId() == id)
                list.add(student);
        }
        return list;
    }
}
