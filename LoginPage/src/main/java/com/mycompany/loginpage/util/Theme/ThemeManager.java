/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginpage.util.Theme;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Enri Miguel Costan
 */

public class ThemeManager {
    public interface ThemeChangeListener {
        void onThemeChanged(boolean darkMode);
    }

    private static ThemeManager instance;
    private boolean darkMode = true; // default: dark
    private final List<ThemeChangeListener> listeners = new CopyOnWriteArrayList<>();

    private ThemeManager() {}

    public static synchronized ThemeManager getInstance() {
        if (instance == null) instance = new ThemeManager();
        return instance;
    }

    public boolean isDark() {
        return darkMode;
    }

    public void setDark(boolean dark) {
        if (this.darkMode == dark) return;
        this.darkMode = dark;
        notifyListeners();
    }

    public void toggle() {
        setDark(!darkMode);
    }

    public void addListener(ThemeChangeListener l) {
        if (l != null) listeners.add(l);
    }

    public void removeListener(ThemeChangeListener l) {
        listeners.remove(l);
    }

    private void notifyListeners() {
        for (ThemeChangeListener l : listeners) {
            try {
                l.onThemeChanged(darkMode);
            } catch (Exception ignored) {}
        }
    }

    // ---------- Color helpers ----------

    /** Menu bar background: WHITE in light mode, BLACK in dark mode (per request). */
    public Color getMenuBarBackground() {
        return darkMode ? Color.BLACK : Color.WHITE;
    }

    /** Menu item background (non-active). */
    public Color getMenuItemBackground() {
        return getMenuBarBackground(); // keep consistent with menu bar
    }

    /** Menu text color (contrasts with menu background). */
    public Color getMenuTextColor() {
        return darkMode ? Color.WHITE : Color.BLACK;
    }

    /** Menu active background for the selected menu item. */
    public Color getMenuActiveBackground() {
        // Use a highlight that shows against black or white
        return darkMode ? new Color(50, 50, 50) : new Color(30, 144, 255);
    }

    /** Hover background color for menu items. */
    public Color getHoverBg() {
        return darkMode ? new Color(70, 70, 85) : new Color(220, 235, 255);
    }

    /** Main card background: complementary dark gray in dark mode, NAVY in light mode. */
    public Color getMainBackground() {
        if (darkMode) {
            // changed to dark gray per your request
            return new Color(45, 45, 45); // dark gray
        } else {
            // light mode -> main panel uses NAVY per your request (0,0,128)
            return new Color(0, 0, 128);
        }
    }

    /** Main text color (contrasts with main background). */
    public Color getMainTextColor() {
        // both modes use light text against dark backgrounds
        return Color.WHITE;
    }

    /** Input field background color (for light/dark comfort). */
    public Color getInputBackground() {
        return darkMode ? new Color(220, 230, 255) : Color.WHITE;
    }

    /** Input text color */
    public Color getInputTextColor() {
        return Color.BLACK;
    }

    /** Secondary content color (e.g., small info text). */
    public Color getSecondaryTextColor() {
        return darkMode ? new Color(200, 200, 210) : new Color(220, 220, 220);
    }
}
