/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginpage.model;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author asjin
 */
public class Teacher {
    private String name;
    private String id;
    private List<String> assignedCourses; // Course codes (e.g., "M101")

    public Teacher(String name, String id) {
        this.name = name;
        this.id = id;
        this.assignedCourses = new ArrayList<>();
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<String> getAssignedCourses() {
        return assignedCourses;
    }

    public void assignCourse(String courseCode) {
        if (!assignedCourses.contains(courseCode)) {
            assignedCourses.add(courseCode);
        }
    }

    public void removeCourse(String courseCode) {
        assignedCourses.remove(courseCode);
    }
}
