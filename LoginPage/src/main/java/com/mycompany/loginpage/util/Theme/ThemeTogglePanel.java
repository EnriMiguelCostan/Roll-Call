/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginpage.util.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Enri Miguel Costan
 */

public class ThemeTogglePanel extends JPanel {
    private final JLabel iconLabel;

    public ThemeTogglePanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        iconLabel = new JLabel();
        iconLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(iconLabel);

        // initial rendering based on current theme
        updateIcon(ThemeManager.getInstance().isDark());

        // toggle when clicked
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ThemeManager.getInstance().toggle();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });

        // listen for theme changes to update icon
        ThemeManager.getInstance().addListener(this::updateIcon);
    }

    private void updateIcon(boolean darkMode) {
        // Show moon for dark, sun for light (icon corresponds to current mode)
        if (darkMode) {
            iconLabel.setText("Light 🌄"); // moon
        } else {
            iconLabel.setText("Dark 🌆"); // sun
        }
        iconLabel.setForeground(ThemeManager.getInstance().getMenuTextColor());
        revalidate();
        repaint();
    }
}
