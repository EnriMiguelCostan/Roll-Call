/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.loginpage;

import com.mycompany.loginpage.model.User;
import com.mycompany.loginpage.model.DataStore;
import com.mycompany.loginpage.util.Theme.ThemeManager;
import com.mycompany.loginpage.util.Theme.ThemeTogglePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class LoginPage extends JFrame {
    private static final String CARD_HOME = "HOME";
    private static final String CARD_ABOUT = "ABOUT";
    private static final String CARD_CONTACT = "CONTACT";
    private static final String CARD_LOGIN = "LOGIN";

    private final Map<String, JPanel> menuItemPanels = new HashMap<>();
    private CardLayout cardLayout;
    private JPanel cards;

    // login fields
    private JTextField userField;
    private JPasswordField passField;

    // logo path - change if needed
    private static final String LOGO_PATH = "src/main/java/com/resources/RollCall.png";

    public LoginPage() {
        setTitle("RollCall");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 680);
        setLocationRelativeTo(null);

        buildUI();

        // apply initial theme and listen for changes
        ThemeManager.getInstance().addListener(dark -> applyTheme());
        applyTheme();

        setVisible(true);
    }

    private void buildUI() {
        getContentPane().setLayout(new BorderLayout());

        // Top container: left = menu bar (white/dark depending on theme), right = theme toggle
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setOpaque(true);
        getContentPane().add(topContainer, BorderLayout.NORTH);

        // Menu bar (FlowLayout) — menu background controlled by ThemeManager
        JPanel menuBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        menuBar.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        menuBar.setOpaque(true);

        // create menu item panels (these are clickable panels)
        createMenuItem(menuBar, "Home", CARD_HOME);
        createMenuItem(menuBar, "About Us", CARD_ABOUT);
        createMenuItem(menuBar, "Contact Us", CARD_CONTACT);
        createMenuItem(menuBar, "Login", CARD_LOGIN);

        topContainer.add(menuBar, BorderLayout.WEST);

        // Theme toggle on the right (a small panel that shows sun/moon)
        ThemeTogglePanel toggle = new ThemeTogglePanel();
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        rightPanel.setOpaque(false);
        rightPanel.add(toggle);
        topContainer.add(rightPanel, BorderLayout.EAST);

        // Cards center area
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        getContentPane().add(cards, BorderLayout.CENTER);

        cards.add(createHomeCard(), CARD_HOME);
        cards.add(createAboutCard(), CARD_ABOUT);
        cards.add(createContactCard(), CARD_CONTACT);
        cards.add(createLoginCard(), CARD_LOGIN);

        // default active
        setActiveMenu(CARD_HOME);
        cardLayout.show(cards, CARD_HOME);
    }

    private void createMenuItem(JPanel parent, String title, String cardName) {
        JPanel item = new JPanel(new GridBagLayout());
        item.setPreferredSize(new Dimension(130, 40));
        item.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        item.setOpaque(true);

        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        item.add(lbl);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setActiveMenu(cardName);
                cardLayout.show(cards, cardName);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isActiveMenu(cardName)) item.setBackground(ThemeManager.getInstance().getHoverBg());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isActiveMenu(cardName)) item.setBackground(ThemeManager.getInstance().getMenuItemBackground());
            }
        });

        parent.add(item);
        menuItemPanels.put(cardName, item);
    }

    private void setActiveMenu(String cardName) {
        for (Map.Entry<String, JPanel> e : menuItemPanels.entrySet()) {
            String key = e.getKey();
            JPanel p = e.getValue();
            JLabel lbl = (JLabel) p.getComponent(0);
            if (key.equals(cardName)) {
                p.setBackground(ThemeManager.getInstance().getMenuActiveBackground());
                lbl.setForeground(ThemeManager.getInstance().getMenuTextColor().equals(Color.BLACK) ? Color.WHITE : Color.WHITE);
            } else {
                p.setBackground(ThemeManager.getInstance().getMenuItemBackground());
                lbl.setForeground(ThemeManager.getInstance().getMenuTextColor());
            }
        }
    }

    private boolean isActiveMenu(String cardName) {
        JPanel p = menuItemPanels.get(cardName);
        if (p == null) return false;
        return p.getBackground().equals(ThemeManager.getInstance().getMenuActiveBackground());
    }

    // ---------- Cards ----------

    private JPanel createHomeCard() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Welcome to RollCall");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        p.add(title, gbc);

        gbc.gridy++;
        JLabel logo = new JLabel();
        ImageIcon icon = scaleImageIcon(LOGO_PATH, 220, 220);
        if (icon != null) logo.setIcon(icon);
        p.add(logo, gbc);

        gbc.gridy++;
        JLabel info = new JLabel("<html><div style='text-align:center;width:560px;'>"
                + "RollCall is a simple enrollment and profile demo. Use the menu above to navigate. "
                + "Click Login to authenticate, or Register to create a new student account."
                + "</div></html>");
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        p.add(info, gbc);

        // Theme updates
        ThemeManager.getInstance().addListener(dark -> applyThemeToComponent(p));
        return p;
    }

    private JPanel createAboutCard() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        p.setOpaque(true);

        JLabel h = new JLabel("About Us");
        h.setFont(new Font("Arial", Font.BOLD, 26));
        p.add(h, BorderLayout.NORTH);

        JTextArea txt = new JTextArea(
                "RollCall is a sample lightweight app demonstrating a student login and enrollment flow.\n\n"
                        + "This demo stores data in an in-memory DataStore and is intended for learning and testing.");
        txt.setEditable(false);
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        JScrollPane sc = new JScrollPane(txt);
        p.add(sc, BorderLayout.CENTER);

        ThemeManager.getInstance().addListener(dark -> applyThemeToComponent(p));
        return p;
    }

    private JPanel createContactCard() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(true);
        p.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel h = new JLabel("Contact Us");
        h.setFont(new Font("Arial", Font.BOLD, 26));
        p.add(h, gbc);

        gbc.gridy++;
        JLabel addr = new JLabel("<html>Address: 123 Example St., Example City<br/>Phone: +63 912 345 6789<br/>Email: info@example.com</html>");
        addr.setFont(new Font("Arial", Font.PLAIN, 14));
        p.add(addr, gbc);

        ThemeManager.getInstance().addListener(dark -> applyThemeToComponent(p));
        return p;
    }

    private JPanel createLoginCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(true);
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("RollCall");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        panel.add(title, gbc);

        gbc.gridy++;
        JLabel logo = new JLabel();
        ImageIcon icon = scaleImageIcon(LOGO_PATH, 180, 180);
        if (icon != null) logo.setIcon(icon);
        panel.add(logo, gbc);

        gbc.gridy++;
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(6, 6, 6, 6);
        f.gridx = 0;
        f.gridy = 0;
        f.anchor = GridBagConstraints.CENTER;
        f.fill = GridBagConstraints.HORIZONTAL;

        JLabel ulabel = new JLabel("Username:");
        ulabel.setFont(new Font("Arial", Font.PLAIN, 16));
        form.add(ulabel, f);

        f.gridy++;
        userField = new JTextField(20);
        userField.setMaximumSize(new Dimension(360, 28));
        form.add(userField, f);

        f.gridy++;
        JLabel plabel = new JLabel("Password:");
        plabel.setFont(new Font("Arial", Font.PLAIN, 16));
        form.add(plabel, f);

        f.gridy++;
        passField = new JPasswordField(20);
        passField.setMaximumSize(new Dimension(360, 28));
        form.add(passField, f);

        f.gridy++;
        JButton loginBtn = new JButton("Login");
        form.add(loginBtn, f);

        f.gridy++;
        JLabel registerLabel = new JLabel("<HTML><U>New student? Register here</U></HTML>");
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        form.add(registerLabel, f);

        panel.add(form, gbc);

        // actions
        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginPage.this, "Please enter both username and password.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            User user = DataStore.users.stream()
                    .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                    .findFirst().orElse(null);
            if (user != null) {
                try {
                    // rollcall UI should also be themed via ThemeManager (we updated RollCall)
                    new RollCall(user.getRole(), user.getId()).setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(LoginPage.this, "Error opening dashboard: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(LoginPage.this,
                        "Invalid credentials. If you are a new student, please register first.",
                        "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new EnrollmentForm().setVisible(true);
                dispose();
            }
        });

        ThemeManager.getInstance().addListener(dark -> applyThemeToComponent(panel));
        return panel;
    }

    // ---------- Theme application helpers ----------

    private void applyTheme() {
        boolean dark = ThemeManager.getInstance().isDark();

        // menu bar background: white (light) or black (dark)
        Component top = getContentPane().getComponent(0);
        if (top instanceof JPanel) {
            // top container has two children: menuBar (WEST) and right area (EAST)
            JPanel topContainer = (JPanel) top;
            // iterate children and find menu bar (FlowLayout with our menu items)
            for (Component c : topContainer.getComponents()) {
                if (c instanceof JPanel) {
                    JPanel p = (JPanel) c;
                    // set background for menu bar specifically (we keep it white in light and black in dark)
                    p.setBackground(ThemeManager.getInstance().getMenuBarBackground());
                    // ensure children updated
                    for (Component ch : p.getComponents()) applyThemeToComponent(ch);
                }
            }
        }

        // Apply to card components
        for (Component card : cards.getComponents()) {
            applyThemeToComponent(card);
        }

        // menu items active state (ensure selected looks correct)
        for (Map.Entry<String, JPanel> e : menuItemPanels.entrySet()) {
            JPanel p = e.getValue();
            // if it's active set to highlight, else menu background
            if (isActiveMenu(e.getKey())) {
                p.setBackground(ThemeManager.getInstance().getMenuActiveBackground());
                ((JLabel) p.getComponent(0)).setForeground(ThemeManager.getInstance().getMenuTextColor().equals(Color.BLACK) ? Color.WHITE : Color.WHITE);
            } else {
                p.setBackground(ThemeManager.getInstance().getMenuItemBackground());
                ((JLabel) p.getComponent(0)).setForeground(ThemeManager.getInstance().getMenuTextColor());
            }
        }

        revalidate();
        repaint();
    }

    private void applyThemeToComponent(Component comp) {
        boolean dark = ThemeManager.getInstance().isDark();
        Color mainBg = ThemeManager.getInstance().getMainBackground();
        Color mainText = ThemeManager.getInstance().getMainTextColor();

        if (comp instanceof JPanel) {
            JPanel p = (JPanel) comp;
            // If the panel is the top menu panel (i.e., it contains menu items), we set its background via applyTheme()
            // Otherwise set main card bg
            if (containsMenuItems(p)) {
                p.setBackground(ThemeManager.getInstance().getMenuBarBackground());
            } else {
                p.setBackground(mainBg);
            }
            for (Component ch : p.getComponents()) applyThemeToComponent(ch);
        } else if (comp instanceof JLabel) {
            JLabel l = (JLabel) comp;
            l.setForeground(mainText);
        } else if (comp instanceof JButton) {
            JButton b = (JButton) comp;
            b.setBackground(dark ? Color.LIGHT_GRAY : new Color(210, 210, 210));
            b.setForeground(Color.BLACK);
        } else if (comp instanceof JTextArea) {
            JTextArea t = (JTextArea) comp;
            t.setBackground(ThemeManager.getInstance().getMainBackground());
            t.setForeground(ThemeManager.getInstance().getSecondaryTextColor());
        } else if (comp instanceof JTextField) {
            JTextField tf = (JTextField) comp;
            tf.setBackground(ThemeManager.getInstance().getInputBackground());
            tf.setForeground(ThemeManager.getInstance().getInputTextColor());
        } else if (comp instanceof JPasswordField) {
            JPasswordField pf = (JPasswordField) comp;
            pf.setBackground(ThemeManager.getInstance().getInputBackground());
            pf.setForeground(ThemeManager.getInstance().getInputTextColor());
        } else if (comp instanceof JScrollPane) {
            JScrollPane sp = (JScrollPane) comp;
            applyThemeToComponent(sp.getViewport().getView());
        }
    }

    private boolean containsMenuItems(JPanel p) {
        // Heuristic: if the panel contains one of our menuItemPanels as direct children, return true
        for (JPanel item : menuItemPanels.values()) {
            if (item.getParent() == p) return true;
        }
        return false;
    }

    // Utility: scale image
    private ImageIcon scaleImageIcon(String path, int w, int h) {
        try {
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception ex) {
            return null;
        }
    }

    // main (for quick manual testing)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}
