/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginpage.model;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static List<User> users = new ArrayList<>();
    public static List<Student> students = new ArrayList<>();
    public static List<Teacher> teachers = new ArrayList<>();
    public static List<Admin> admins = new ArrayList<>();
    public static List<Course> courses = new ArrayList<>();

    static {
        // Sample data for testing
        users.add(new User("teacher1", "AdDUJaneSmith", "teacher", "T001"));
        users.add(new User("admin1", "AdDUAdmin", "admin", "A001"));
        teachers.add(new Teacher("T001", "Jane Smith"));
        admins.add(new Admin("A001", "AdminU"));
        courses.add(new Course("M101", "Mathematics"));
        courses.add(new Course("P102", "Physics"));
    }
}

