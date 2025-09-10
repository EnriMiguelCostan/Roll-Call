/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginpage.model;

/**
 *
 * @author asjin
 */
public class User {
    private String username;
    private String password;
    private String role; // "student", "teacher", or "admin"
    private String id; // Links to Student, Teacher, or Admin ID

    public User(String username, String password, String role, String id) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getId() {
        return id;
    }
}
