package com.megacitycab.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.megacitycab.controller.UserController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LoginForm extends JFrame {
    
    private JPanel mainPanel;
    private JPanel formPanel;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnExit;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JLabel logoLabel;
    
    private UserController userController;
    
    // Define colors
    private final Color PRIMARY_COLOR = new Color(25, 118, 210);
    private final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private final Color DARK_TEXT = new Color(33, 33, 33);
    private final Color LIGHT_TEXT = new Color(158, 158, 158);
    
    public LoginForm() {
        // Initialize components
        initComponents();
        
        // Set up controller
        userController = new UserController();
        
        // Set up FlatLaf look and feel
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf: " + ex);
        }
    }
    
    private void initComponents() {
        // Main container with card layout effect
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Logo and branding section (left panel)
        JPanel brandPanel = createBrandPanel();
        
        // Form panel (right panel)
        formPanel = createFormPanel();
        
        // Add panels to main container
        mainPanel.add(brandPanel, BorderLayout.WEST);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Set up the frame
        setTitle("Mega City Cab Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(850, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private JPanel createBrandPanel() {
        JPanel brandPanel = new JPanel();
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));
        brandPanel.setBackground(PRIMARY_COLOR);
        brandPanel.setPreferredSize(new Dimension(350, 500));
        
        // Create a logo placeholder
        logoLabel = new JLabel("");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 80));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(new EmptyBorder(80, 0, 20, 0));
        
        // App name
        JLabel appNameLabel = new JLabel("MEGA CITY CAB");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        appNameLabel.setForeground(Color.WHITE);
        appNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Tagline
        JLabel taglineLabel = new JLabel("Management System");
        taglineLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        taglineLabel.setForeground(new Color(255, 255, 255, 200));
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Feature points
        String[] features = {
            "Manage Drivers",
            "Track Vehicles",
            "Schedule Rides",
            "Monitor Performance"
        };
        
        JPanel featurePanel = new JPanel();
        featurePanel.setLayout(new BoxLayout(featurePanel, BoxLayout.Y_AXIS));
        featurePanel.setBackground(PRIMARY_COLOR);
        featurePanel.setBorder(new EmptyBorder(60, 0, 0, 0));
        featurePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        for (String feature : features) {
            JLabel featureLabel = new JLabel(feature);
            featureLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            featureLabel.setForeground(Color.WHITE);
            featureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            featureLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
            featurePanel.add(featureLabel);
        }
        
        // Add components to brand panel
        brandPanel.add(logoLabel);
        brandPanel.add(appNameLabel);
        brandPanel.add(taglineLabel);
        brandPanel.add(featurePanel);
        
        return brandPanel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(60, 50, 60, 50));
        
        // Title
        lblTitle = new JLabel("Welcome Back");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(DARK_TEXT);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Subtitle
        lblSubtitle = new JLabel("Please login to your account");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitle.setForeground(LIGHT_TEXT);
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSubtitle.setBorder(new EmptyBorder(5, 0, 30, 0));
        
        // Username field with label
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Arial", Font.BOLD, 12));
        userLabel.setForeground(LIGHT_TEXT);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        userLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
        
        txtUsername = createStyledTextField("Enter your username");
        txtUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Password field with label
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("Arial", Font.BOLD, 12));
        passLabel.setForeground(LIGHT_TEXT);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passLabel.setBorder(new EmptyBorder(20, 0, 5, 0));
        
        txtPassword = createStyledPasswordField("Enter your password");
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        // Login button
        btnLogin = createStyledButton("LOGIN", PRIMARY_COLOR, Color.WHITE);
        btnLogin.addActionListener((ActionEvent e) -> {
            loginAction();
        });
        
        // Exit button
        btnExit = createStyledButton("EXIT", Color.WHITE, DARK_TEXT);
        btnExit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        buttonPanel.add(btnExit);
        
        // Add components to form panel
        panel.add(lblTitle);
        panel.add(lblSubtitle);
        panel.add(userLabel);
        panel.add(txtUsername);
        panel.add(passLabel);
        panel.add(txtPassword);
        panel.add(buttonPanel);
        
        return panel;
    }
    
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(20);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setPreferredSize(new Dimension(300, 40));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, LIGHT_TEXT),
            new EmptyBorder(5, 5, 5, 5)
        ));
        
        // Placeholder functionality
        field.setText(placeholder);
        field.setForeground(LIGHT_TEXT);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(DARK_TEXT);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(LIGHT_TEXT);
                }
            }
        });
        
        return field;
    }
    
    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(20);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setPreferredSize(new Dimension(300, 40));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setEchoChar((char) 0); // Initially show placeholder text
        field.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, LIGHT_TEXT),
            new EmptyBorder(5, 5, 5, 5)
        ));
        
        // Placeholder functionality
        field.setText(placeholder);
        field.setForeground(LIGHT_TEXT);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setEchoChar('•');
                    field.setForeground(DARK_TEXT);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(field.getPassword()).isEmpty()) {
                    field.setText(placeholder);
                    field.setEchoChar((char) 0);
                    field.setForeground(LIGHT_TEXT);
                }
            }
        });
        
        return field;
    }
    
    private JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 40));
        button.setMaximumSize(new Dimension(120, 40));
        
        return button;
    }
    
    private void loginAction() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        
        // Check if fields contain placeholders
        if (username.equals("Enter your username") || 
            password.equals("Enter your password") ||
            username.isEmpty() || password.isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Username and password cannot be empty", 
                "Login Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean isAuthenticated = userController.authenticate(username, password);
        
        if (isAuthenticated) {
            String role = userController.getUserRole(username);
            JOptionPane.showMessageDialog(this, 
                "Login successful! Welcome, " + username, 
                "Login Success", 
                JOptionPane.INFORMATION_MESSAGE);
                
            // Open main dashboard based on role
            openDashboard(username, role);
            
            // Close login form
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Invalid username or password", 
                "Login Failed", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openDashboard(String username, String role) {
        MainDashboard dashboard = new MainDashboard(username, role);
        dashboard.setVisible(true);
    }
    
    public static void main(String[] args) {
        // Set system properties for better rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        // Set up FlatLaf for the entire application
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            
            // Customize UI defaults
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("ProgressBar.arc", 10);
            UIManager.put("TextComponent.arc", 10);
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }
        
        // Create and display the login form
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}