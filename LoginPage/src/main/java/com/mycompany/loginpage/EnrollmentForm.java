/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginpage;

import com.mycompany.loginpage.model.Student;
import com.mycompany.loginpage.model.User;
import com.mycompany.loginpage.model.DataStore;
import com.mycompany.loginpage.model.Course;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class EnrollmentForm extends JFrame {
    private JTextField nameField;
    private JTextField ageField;
    private JTextField addressField;
    private JTextField phoneField;
    private JComboBox<String> yearCombo;
    private JComboBox<String> genderCombo;
    private JComboBox<String> courseCombo;
    private JLabel imageLabel;
    private String selectedImagePath;

    public EnrollmentForm() {
        setTitle("Student Enrollment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 650); // Increased height for image upload
        setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0, 0, 128));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Student Enrollment");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nameLabel = new JLabel("Name (First Last):");
        nameLabel.setForeground(Color.WHITE);
        nameField = new JTextField(20);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setForeground(Color.WHITE);
        ageField = new JTextField(20);

        JLabel yearLabel = new JLabel("School Year:");
        yearLabel.setForeground(Color.WHITE);
        String[] years = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
        yearCombo = new JComboBox<>(years);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setForeground(Color.WHITE);
        String[] genders = {"Male", "Female", "Other"};
        genderCombo = new JComboBox<>(genders);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(Color.WHITE);
        addressField = new JTextField(20);

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setForeground(Color.WHITE);
        String[] courses = DataStore.courses.stream()
            .map(Course::getName)
            .toArray(String[]::new);
        courseCombo = new JComboBox<>(courses.length > 0 ? courses : new String[]{"Mathematics", "Physics"});

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setForeground(Color.WHITE);
        phoneField = new JTextField(20);

        JLabel imageUploadLabel = new JLabel("Profile Picture:");
        imageUploadLabel.setForeground(Color.WHITE);
        JButton uploadButton = new JButton("Upload Image");
        imageLabel = new JLabel("No image selected");
        imageLabel.setForeground(Color.WHITE);

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(ageLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(yearLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(yearCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(genderLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(genderCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(addressLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(courseLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(courseCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(imageUploadLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(uploadButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        formPanel.add(imageLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(0, 0, 128));
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel);

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "png"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedImagePath = selectedFile.getAbsolutePath();
                imageLabel.setText("Selected: " + selectedFile.getName());
            }
        });

        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String age = ageField.getText().trim();
            String year = (String) yearCombo.getSelectedItem();
            String gender = (String) genderCombo.getSelectedItem();
            String address = addressField.getText().trim();
            String course = (String) courseCombo.getSelectedItem();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || age.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate name format (expecting first and last name)
            String[] nameParts = name.split("\\s+");
            if (nameParts.length < 2) {
                JOptionPane.showMessageDialog(this, "Please enter both first and last name (e.g., Rose Gomez).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Generate username: first letter of first name + last name
            String firstName = nameParts[0];
            String lastName = nameParts[nameParts.length - 1];
            String username = (firstName.charAt(0) + lastName).toLowerCase().replaceAll("\\s+", "");
            String password = "AdDU" + name.toLowerCase().replaceAll("\\s+", "");

            // Validate and copy image
            String savedImagePath = null;
            if (selectedImagePath != null) {
                try {
                    File sourceFile = new File(selectedImagePath);
                    String extension = selectedImagePath.substring(selectedImagePath.lastIndexOf("."));
                    File destDir = new File("src/main/resources/students/");
                    destDir.mkdirs();
                    String studentId = "S" + String.format("%03d", DataStore.students.size() + 1);
                    File destFile = new File(destDir, studentId + extension);
                    Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    savedImagePath = destFile.getPath();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
                int ageValue = Integer.parseInt(age);
                if (ageValue <= 0) {
                    JOptionPane.showMessageDialog(this, "Age must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Generate unique ID
                String id = "S" + String.format("%03d", DataStore.students.size() + 1);

                // Create Student and User
                Student student = new Student(id, name, ageValue, year, gender, address, course, phone, savedImagePath);
                DataStore.students.add(student);
                DataStore.users.add(new User(username, password, "student", id));

                // Enroll in selected course
                Course selectedCourse = DataStore.courses.stream()
                    .filter(c -> c.getName().equals(course))
                    .findFirst().orElse(null);
                if (selectedCourse != null) {
                    student.enrollCourse(selectedCourse.getCode());
                    selectedCourse.addStudent(id);
                }

                JOptionPane.showMessageDialog(this, 
                    "Enrollment successful!\nUsername: " + username + "\nPassword: " + password, 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new LoginPage().setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Age must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            dispose();
            new LoginPage().setVisible(true);
        });
    }
}
