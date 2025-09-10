/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginpage.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {
    public String id;
    public String name;
    public int age;
    public String schoolYear;
    public String gender;
    public String address;
    public String course;
    public String phoneNumber;
    public String imagePath; // New field for image path
    public List<String> enrolledCourses;
    public List<String> grades;

    public Student(String id, String name, int age, String schoolYear, String gender, String address, String course, String phoneNumber, String imagePath) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.schoolYear = schoolYear;
        this.gender = gender;
        this.address = address;
        this.course = course;
        this.phoneNumber = phoneNumber;
        this.imagePath = imagePath;
        this.enrolledCourses = new ArrayList<>();
        this.grades = new ArrayList<>();
    }
    
    public Student(String id, String name, int age, String schoolYear, String gender, String address, String course, String imagePath) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.schoolYear = schoolYear;
        this.gender = gender;
        this.address = address;
        this.course = course;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getCourse() {
        return course;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public List<String> getGrades() {
        return grades;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public void enrollCourse(String courseCode) {
        if (!enrolledCourses.contains(courseCode)) {
            enrolledCourses.add(courseCode);
        }
    }

    public void dropCourse(String courseCode) {
        enrolledCourses.remove(courseCode);
        grades.removeIf(grade -> grade.startsWith(courseCode + ":"));
    }

    public void addGrade(String courseCode, String grade) {
        grades.add(courseCode + ": " + grade);
    }
}

