/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginpage;

import com.mycompany.loginpage.model.Course;
import com.mycompany.loginpage.model.DataStore;
import com.mycompany.loginpage.model.Student;
import com.mycompany.loginpage.model.User;
import com.mycompany.loginpage.util.Theme.ThemeManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.stream.Collectors;

/**
 * Complete RollCall JFrame — left menu + content area + theme integration.
 * Replace/merge with your existing RollCall.java.
 */
public class RollCall extends JFrame {

    private final String role;
    private final String userId;

    private JPanel menuPanel;
    private JPanel contentPanel;
    private ThemeManager.ThemeChangeListener themeListener;

    private static final int MENU_WIDTH = 260;
    private static final String LOGO_PATH = "src/main/java/com/resources/RollCall.png";

    public RollCall(String role, String userId) {
        super("RollCall - Dashboard");
        this.role = role == null ? "student" : role;
        this.userId = userId;

        initUI();

        // Register theme listener and apply initial theme
        themeListener = dark -> SwingUtilities.invokeLater(this::applyThemeToRollCall);
        ThemeManager.getInstance().addListener(themeListener);
        applyThemeToRollCall();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 720);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        getContentPane().setLayout(new BorderLayout());

        // Left menu
        menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(MENU_WIDTH, 0));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel appLabel = new JLabel("RollCall");
        appLabel.setFont(new Font("Arial", Font.BOLD, 22));
        appLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuPanel.add(appLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Add role-specific items
        if ("student".equalsIgnoreCase(role)) {
            menuPanel.add(createMenuPanel("Home", this::showHomePage));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            menuPanel.add(createMenuPanel("Profile", this::showStudentProfile));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            menuPanel.add(createMenuPanel("Courses", this::showAvailableCourses));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            menuPanel.add(createMenuPanel("Grades", this::showGrades));
        } else if ("teacher".equalsIgnoreCase(role)) {
            menuPanel.add(createMenuPanel("Home", this::showHomePage));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            menuPanel.add(createMenuPanel("Profile", this::showTeacherProfile));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            menuPanel.add(createMenuPanel("My Courses", this::showTeacherCourses));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            menuPanel.add(createMenuPanel("Students", this::showEnrolledStudents));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            menuPanel.add(createMenuPanel("Grade Mgmt", this::showGradeManagement));
        } else {
            menuPanel.add(createMenuPanel("Home", this::showHomePage));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            menuPanel.add(createMenuPanel("Manage Students", this::showManageStudents));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            menuPanel.add(createMenuPanel("Manage Teachers", this::showManageTeachers));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            menuPanel.add(createMenuPanel("Manage Courses", this::showManageCourses));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            menuPanel.add(createMenuPanel("Enrollment", this::showControlEnrollment));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            menuPanel.add(createMenuPanel("Reports", this::showReports));
        }

        menuPanel.add(Box.createVerticalGlue());

        JPanel logoutP = createMenuPanel("Log out", () -> {
            int confirm = JOptionPane.showConfirmDialog(RollCall.this, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
            }
        });
        logoutP.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuPanel.add(logoutP);

        getContentPane().add(menuPanel, BorderLayout.WEST);

        // Content area
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Show initial page
        showHomePage();
    }

    /** Creates a clickable menu row (panel). */
    private JPanel createMenuPanel(String text, Runnable action) {
        JPanel p = new JPanel(new BorderLayout());
        p.setMaximumSize(new Dimension(MENU_WIDTH - 24, 46));
        p.setPreferredSize(new Dimension(MENU_WIDTH - 24, 46));
        p.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));
        p.add(lbl, BorderLayout.CENTER);

        p.addMouseListener(new MouseAdapter() {
            Color originalBg;

            @Override
            public void mouseEntered(MouseEvent e) {
                originalBg = p.getBackground();
                p.setBackground(ThemeManager.getInstance().getHoverBg());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p.setBackground(originalBg == null ? ThemeManager.getInstance().getMenuItemBackground() : originalBg);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
                // visually mark active: set border for clicked panel and clear others
                for (Component c : menuPanel.getComponents()) {
                    if (c instanceof JPanel) {
                        ((JPanel) c).setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
                    }
                }
                p.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ThemeManager.getInstance().getMenuActiveBackground(), 2),
                        BorderFactory.createEmptyBorder(4, 6, 4, 6)
                ));
            }
        });

        return p;
    }

    // ---------------- Content pages ----------------

    private void showHomePage() {
        contentPanel.removeAll();

        JPanel home = new JPanel(new BorderLayout());
        home.setOpaque(true);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);

        JLabel title = new JLabel("Welcome to RollCall");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(title);
        center.add(Box.createRigidArea(new Dimension(0, 12)));

        JLabel logo = new JLabel();
        ImageIcon icon = scaleImageIcon(LOGO_PATH, 220, 220);
        if (icon != null) {
            logo.setIcon(icon);
            logo.setAlignmentX(Component.CENTER_ALIGNMENT);
            center.add(logo);
            center.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        JLabel info = new JLabel("<html><div style='text-align:center;width:620px;'>"
                + "Use the menu on the left to navigate your account. Students can view profile, enroll in courses and view grades. "
                + "Teachers can manage their courses and grades. Admins can manage users and courses."
                + "</div></html>");
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(info);

        home.add(center, BorderLayout.CENTER);

        contentPanel.add(home, BorderLayout.CENTER);
        revalidate();
        repaint();
        applyThemeToRollCall();
    }

    private void showStudentProfile() {
        contentPanel.removeAll();

        Student student = findStudentByUserId(userId);
        if (student == null) {
            contentPanel.add(new JLabel("Student not found.", SwingConstants.CENTER), BorderLayout.CENTER);
            revalidate();
            repaint();
            return;
        }

        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 3, true),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        profilePanel.setOpaque(true);

        // Top row: title (left) and photo (right)
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);

        JLabel title = new JLabel("Profile");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(ThemeManager.getInstance().getMainTextColor());
        topRow.add(title, BorderLayout.WEST);

        JLabel photoLabel = new JLabel();
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        String imgPath = student.getImagePath();
        if (imgPath != null && !imgPath.trim().isEmpty()) {
            ImageIcon sc = scaleImageIcon(imgPath, 140, 140);
            if (sc != null) photoLabel.setIcon(sc);
        }
        photoLabel.setPreferredSize(new Dimension(140, 140));
        photoLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        JPanel photoWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        photoWrap.setOpaque(false);
        photoWrap.add(photoLabel);
        topRow.add(photoWrap, BorderLayout.EAST);

        profilePanel.add(topRow, BorderLayout.NORTH);

        // Info panel: two-column GridBagLayout
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font valueFont = new Font("Arial", Font.PLAIN, 18);

        String[][] rows = {
                {"Name:", student.getName()},
                {"ID:", student.getId()},
                {"Age:", String.valueOf(student.getAge())},
                {"School Year:", student.getSchoolYear()},
                {"Gender:", student.getGender()},
                {"Address:", student.getAddress()},
                {"Course:", student.getCourse()},
                {"Phone:", student.getPhoneNumber()}
        };

        for (int i = 0; i < rows.length; i++) {
            gbc.gridx = 0;
            gbc.weightx = 0.25;
            gbc.anchor = GridBagConstraints.EAST;
            JLabel lbl = new JLabel(rows[i][0]);
            lbl.setFont(labelFont);
            lbl.setForeground(ThemeManager.getInstance().getMainTextColor());
            infoPanel.add(lbl, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.75;
            gbc.anchor = GridBagConstraints.WEST;
            JLabel val = new JLabel(rows[i][1] == null ? "" : rows[i][1]);
            val.setFont(valueFont);
            val.setForeground(ThemeManager.getInstance().getMainTextColor());
            infoPanel.add(val, gbc);

            gbc.gridy++;
        }

        JScrollPane scroll = new JScrollPane(infoPanel);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        profilePanel.add(scroll, BorderLayout.CENTER);

        // Edit button left-aligned below content
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setOpaque(false);
        JButton editBtn = new JButton("Edit Profile");
        editBtn.setBackground(Color.WHITE);
        editBtn.addActionListener(e -> showEditProfileDialog(student));
        bottomPanel.add(editBtn);
        profilePanel.add(bottomPanel, BorderLayout.SOUTH);

        contentPanel.add(profilePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        applyThemeToRollCall();
    }

    private void showEditProfileDialog(Student student) {
        final JDialog dialog = new JDialog(this, "Edit Profile", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        // Name
        gbc.gridx = 0;
        gbc.weightx = 0.25;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel nameLbl = new JLabel("Name:");
        nameLbl.setFont(labelFont);
        form.add(nameLbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.75;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField nameField = new JTextField(student.getName(), 20);
        nameField.setFont(fieldFont);
        form.add(nameField, gbc);
        gbc.gridy++;

        // Age
        gbc.gridx = 0;
        gbc.weightx = 0.25;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel ageLbl = new JLabel("Age:");
        ageLbl.setFont(labelFont);
        form.add(ageLbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.75;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField ageField = new JTextField(String.valueOf(student.getAge()), 6);
        ageField.setFont(fieldFont);
        form.add(ageField, gbc);
        gbc.gridy++;

        // School Year
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel yearLbl = new JLabel("School Year:");
        yearLbl.setFont(labelFont);
        form.add(yearLbl, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JComboBox<String> yearCombo = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        yearCombo.setSelectedItem(student.getSchoolYear());
        form.add(yearCombo, gbc);
        gbc.gridy++;

        // Gender
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel genderLbl = new JLabel("Gender:");
        genderLbl.setFont(labelFont);
        form.add(genderLbl, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderCombo.setSelectedItem(student.getGender());
        form.add(genderCombo, gbc);
        gbc.gridy++;

        // Address (multi-line)
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        JLabel addrLbl = new JLabel("Address:");
        addrLbl.setFont(labelFont);
        form.add(addrLbl, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextArea addrArea = new JTextArea(student.getAddress() == null ? "" : student.getAddress(), 4, 20);
        addrArea.setLineWrap(true);
        addrArea.setWrapStyleWord(true);
        addrArea.setFont(fieldFont);
        JScrollPane addrScroll = new JScrollPane(addrArea);
        form.add(addrScroll, gbc);
        gbc.gridy++;

        // Course combo
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel courseLbl = new JLabel("Course:");
        courseLbl.setFont(labelFont);
        form.add(courseLbl, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        String[] courseNames = DataStore.courses.stream().map(Course::getName).toArray(String[]::new);
        JComboBox<String> courseCombo = new JComboBox<>(courseNames);
        courseCombo.setSelectedItem(student.getCourse());
        form.add(courseCombo, gbc);
        gbc.gridy++;

        // Phone
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel phoneLbl = new JLabel("Phone:");
        phoneLbl.setFont(labelFont);
        form.add(phoneLbl, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField phoneField = new JTextField(student.getPhoneNumber() == null ? "" : student.getPhoneNumber(), 15);
        phoneField.setFont(fieldFont);
        form.add(phoneField, gbc);
        gbc.gridy++;

        // Photo row: preview + change button
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel photoLbl = new JLabel("Photo:");
        photoLbl.setFont(labelFont);
        form.add(photoLbl, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        photoPanel.setOpaque(false);
        JLabel photoPreview = new JLabel();
        photoPreview.setPreferredSize(new Dimension(100, 100));
        photoPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        if (student.getImagePath() != null && !student.getImagePath().trim().isEmpty()) {
            ImageIcon scaled = scaleImageIcon(student.getImagePath(), 100, 100);
            if (scaled != null) photoPreview.setIcon(scaled);
        }
        JButton changePhotoBtn = new JButton("Change Photo");
        final String[] selectedImagePath = { student.getImagePath() };
        changePhotoBtn.addActionListener(ev -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int res = chooser.showOpenDialog(dialog);
            if (res == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                selectedImagePath[0] = path;
                ImageIcon sc = scaleImageIcon(path, 100, 100);
                if (sc != null) photoPreview.setIcon(sc);
                else photoPreview.setIcon(null);
            }
        });
        photoPanel.add(photoPreview);
        photoPanel.add(changePhotoBtn);
        form.add(photoPanel, gbc);
        gbc.gridy++;

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        buttons.add(saveBtn);
        buttons.add(cancelBtn);

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        form.add(buttons, gbc);

        saveBtn.addActionListener(ev -> {
            String newName = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Name is required.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int newAge;
            try {
                newAge = Integer.parseInt(ageText);
                if (newAge <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid positive integer for Age.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Apply changes
            student.setName(newName);
            student.setAge(newAge);
            student.setSchoolYear((String) yearCombo.getSelectedItem());
            student.setGender((String) genderCombo.getSelectedItem());
            student.setAddress(addrArea.getText().trim());
            student.setCourse((String) courseCombo.getSelectedItem());
            student.setPhoneNumber(phoneField.getText().trim());
            if (selectedImagePath[0] != null) student.setImagePath(selectedImagePath[0]);

            dialog.dispose();
            showStudentProfile(); // refresh
        });

        cancelBtn.addActionListener(ev -> dialog.dispose());

        dialog.getContentPane().add(form);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showAvailableCourses() {
        contentPanel.removeAll();

        String[] cols = {"Name", "Code", "Enrolled"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (Course c : DataStore.courses) {
            model.addRow(new Object[]{c.getName(), c.getCode(), c.getEnrolledStudents().size()});
        }
        JTable table = new JTable(model);
        JScrollPane sc = new JScrollPane(table);
        contentPanel.add(sc, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton enroll = new JButton("Enroll (selected)");
        JButton drop = new JButton("Drop (selected)");
        bottom.add(enroll);
        bottom.add(drop);
        contentPanel.add(bottom, BorderLayout.SOUTH);

        enroll.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                String code = (String) model.getValueAt(r, 1);
                Student s = findStudentByUserId(userId);
                if (s != null) {
                    if (!s.getEnrolledCourses().contains(code)) {
                        s.enrollCourse(code);
                        Course course = DataStore.courses.stream().filter(c -> c.getCode().equals(code)).findFirst().orElse(null);
                        if (course != null) course.addStudent(s.getId());
                        JOptionPane.showMessageDialog(this, "Enrolled in " + code);
                    } else {
                        JOptionPane.showMessageDialog(this, "Already enrolled in " + code);
                    }
                }
            }
        });

        drop.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                String code = (String) model.getValueAt(r, 1);
                Student s = findStudentByUserId(userId);
                if (s != null) {
                    if (s.getEnrolledCourses().contains(code)) {
                        s.dropCourse(code);
                        Course course = DataStore.courses.stream().filter(c -> c.getCode().equals(code)).findFirst().orElse(null);
                        if (course != null) course.removeStudent(s.getId());
                        JOptionPane.showMessageDialog(this, "Dropped " + code);
                    } else {
                        JOptionPane.showMessageDialog(this, "Not enrolled in " + code);
                    }
                }
            }
        });

        revalidate();
        repaint();
        applyThemeToRollCall();
    }

    private void showGrades() {
        contentPanel.removeAll();
        Student s = findStudentByUserId(userId);
        if (s == null) {
            contentPanel.add(new JLabel("No student found", SwingConstants.CENTER), BorderLayout.CENTER);
            revalidate();
            repaint();
            return;
        }
        DefaultListModel<String> lm = new DefaultListModel<>();
        for (String g : s.getGrades()) lm.addElement(g);
        JList<String> list = new JList<>(lm);
        contentPanel.add(new JScrollPane(list), BorderLayout.CENTER);

        revalidate();
        repaint();
        applyThemeToRollCall();
    }

    // Placeholders for teacher/admin
    private void showTeacherProfile() { showPlaceholder("Teacher profile (to be implemented)"); }
    private void showTeacherCourses() { showPlaceholder("Teacher courses (to be implemented)"); }
    private void showEnrolledStudents() { showPlaceholder("Enrolled students (to be implemented)"); }
    private void showGradeManagement() { showPlaceholder("Grade management (to be implemented)"); }
    private void showManageStudents() { showPlaceholder("Manage students (to be implemented)"); }
    private void showManageTeachers() { showPlaceholder("Manage teachers (to be implemented)"); }
    private void showManageCourses() { showPlaceholder("Manage courses (to be implemented)"); }
    private void showControlEnrollment() { showPlaceholder("Enrollment control (to be implemented)"); }
    private void showReports() { showPlaceholder("Reports (to be implemented)"); }

    private void showPlaceholder(String text) {
        contentPanel.removeAll();
        contentPanel.add(new JLabel(text, SwingConstants.CENTER), BorderLayout.CENTER);
        revalidate();
        repaint();
        applyThemeToRollCall();
    }

    // ---------- Theme application ----------

    private void applyThemeToRollCall() {
        Color mainBg = ThemeManager.getInstance().getMainBackground();
        Color mainText = ThemeManager.getInstance().getMainTextColor();
        Color menuBg = ThemeManager.getInstance().getMenuBarBackground();
        Color menuItemBg = ThemeManager.getInstance().getMenuItemBackground();
        Color menuActive = ThemeManager.getInstance().getMenuActiveBackground();
        Color menuText = ThemeManager.getInstance().getMenuTextColor();

        // menu
        menuPanel.setBackground(menuBg);
        for (Component c : menuPanel.getComponents()) {
            if (c instanceof JPanel) {
                JPanel item = (JPanel) c;
                item.setBackground(menuItemBg);
                for (Component child : item.getComponents()) {
                    if (child instanceof JLabel) ((JLabel) child).setForeground(menuText);
                }
            } else if (c instanceof JLabel) {
                c.setForeground(menuText);
            }
        }

        // content
        contentPanel.setBackground(mainBg);
        for (Component comp : contentPanel.getComponents()) {
            applyThemeToComponent(comp, mainBg, mainText);
        }

        revalidate();
        repaint();
    }

    private void applyThemeToComponent(Component comp, Color bg, Color fg) {
        if (comp instanceof JPanel) {
            JPanel p = (JPanel) comp;
            if (p != menuPanel) p.setBackground(bg);
            for (Component c : p.getComponents()) applyThemeToComponent(c, bg, fg);
        } else if (comp instanceof JLabel) {
            ((JLabel) comp).setForeground(fg);
        } else if (comp instanceof JButton) {
            ((JButton) comp).setBackground(Color.LIGHT_GRAY);
            ((JButton) comp).setForeground(Color.BLACK);
        } else if (comp instanceof JTable) {
            JTable t = (JTable) comp;
            t.setBackground(bg);
            t.setForeground(fg);
            t.getTableHeader().setBackground(bg.darker());
            t.getTableHeader().setForeground(fg);
        } else if (comp instanceof JScrollPane) {
            JScrollPane sp = (JScrollPane) comp;
            Component vp = sp.getViewport().getView();
            if (vp != null) applyThemeToComponent(vp, bg, fg);
        } else if (comp instanceof JList) {
            ((JList<?>) comp).setBackground(bg);
            ((JList<?>) comp).setForeground(fg);
        } else if (comp instanceof JTextArea) {
            JTextArea t = (JTextArea) comp;
            t.setBackground(bg);
            t.setForeground(fg);
        }
    }

    // ---------- Utilities ----------

    private Student findStudentByUserId(String uid) {
        if (uid == null) return null;
        return DataStore.students.stream().filter(s -> s.getId().equals(uid)).findFirst().orElse(null);
    }

    private ImageIcon scaleImageIcon(String path, int w, int h) {
        try {
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void dispose() {
        // unregister theme listener
        try {
            ThemeManager.getInstance().removeListener(themeListener);
        } catch (Exception ignored) {}
        super.dispose();
    }

    // ---------- Quick test main ----------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // seed minimal DataStore if empty for testing
            if (DataStore.courses.isEmpty()) {
                DataStore.courses.add(new Course("M101", "Mathematics"));
                DataStore.courses.add(new Course("P102", "Physics"));
            }
            if (DataStore.users.isEmpty()) {
                DataStore.users.add(new User("student1", "pass", "student", "S1"));
            }
            if (DataStore.students.isEmpty()) {
                DataStore.students.add(new Student("S1", "Test Student", 18, "1", "Male", "Address", "BS CS", ""));
            }

            RollCall rc = new RollCall("student", "S1");
            rc.setVisible(true);
        });
    }
}
