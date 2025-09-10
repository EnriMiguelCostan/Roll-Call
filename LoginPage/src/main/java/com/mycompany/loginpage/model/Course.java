/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginpage.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String code;
    private String name;
    private List<String> enrolledStudents;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
        this.enrolledStudents = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<String> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void addStudent(String studentId) {
        if (!enrolledStudents.contains(studentId)) {
            enrolledStudents.add(studentId);
        }
    }

    public void removeStudent(String studentId) {
        enrolledStudents.remove(studentId);
    }
}
