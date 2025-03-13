package com.megacitycab.ui;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainDashboard extends JFrame {
    
    private JPanel mainPanel;
    private JLabel lblWelcome;
    private JButton btnCustomer;
    private JButton btnBooking;
    private JButton btnBilling;
    private JButton btnCars;
    private JButton btnDrivers;
    private JButton btnReports;
    private JButton btnHelp;
    private JButton btnLogout;
    
    private String username;
    private String role;
    
    public MainDashboard(String username, String role) {
        this.username = username;
        this.role = role;
        
        initComponents();
        
        // Set up FlatLaf look and feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf: " + ex);
        }
        
        // Set up role-based access
        setupRoleBasedAccess();
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        
        // Welcome message
        lblWelcome = new JLabel("Welcome, " + username + " [" + role + "]");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(lblWelcome, BorderLayout.WEST);
        
        // Logout button
        btnLogout = new JButton("Logout");
        btnLogout.addActionListener((ActionEvent e) -> {
            logout();
        });
        headerPanel.add(btnLogout, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Menu panel
        JPanel menuPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        
        // Customer button
        btnCustomer = createMenuButton("Customer Management", "Manage customer information");
        btnCustomer.addActionListener((ActionEvent e) -> {
            openCustomerManagement();
        });
        menuPanel.add(btnCustomer);
        
        // Booking button
        btnBooking = createMenuButton("Booking Management", "Create and manage bookings");
        btnBooking.addActionListener((ActionEvent e) -> {
            openBookingManagement();
        });
        menuPanel.add(btnBooking);
        
        // Billing button
        btnBilling = createMenuButton("Billing Management", "Calculate and manage bills");
        btnBilling.addActionListener((ActionEvent e) -> {
            openBillingManagement();
        });
        menuPanel.add(btnBilling);
        
        // Cars button
        btnCars = createMenuButton("Car Management", "Manage car information");
        btnCars.addActionListener((ActionEvent e) -> {
            openCarManagement();
        });
        menuPanel.add(btnCars);
        
        // Drivers button
        btnDrivers = createMenuButton("Driver Management", "Manage driver information");
        btnDrivers.addActionListener((ActionEvent e) -> {
            openDriverManagement();
        });
        menuPanel.add(btnDrivers);
        
        // Reports button
        btnReports = createMenuButton("Reports", "View and generate reports");
        btnReports.addActionListener((ActionEvent e) -> {
            openReports();
        });
        menuPanel.add(btnReports);
        
        // Help button
        btnHelp = createMenuButton("Help", "System usage guidelines");
        btnHelp.addActionListener((ActionEvent e) -> {
            openHelp();
        });
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btnHelp);
        
        mainPanel.add(menuPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Set up the frame
        setTitle("Mega City Cab - Main Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private JButton createMenuButton(String title, String toolTip) {
        JButton button = new JButton(title);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setToolTipText(toolTip);
        return button;
    }
    
    private void setupRoleBasedAccess() {
        // Add role-based restrictions here
        if (role.equals("Operator")) {
            // Operators can't access reports
            btnReports.setEnabled(false);
        }
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginForm().setVisible(true);
        }
    }
    
    private void openCustomerManagement() {
         CustomerManagement customerForm = new CustomerManagement();
        customerForm.setVisible(true);
      
    }
    
    private void openBookingManagement() {
        BookingManagementForm bookingForm = new BookingManagementForm();
        bookingForm.setVisible(true);
    }
    
  private void openBillingManagement() {
    BillingManagementForm billingForm = new BillingManagementForm();
    billingForm.setVisible(true);
}
    
    private void openCarManagement() {
        CarManagement carManagement = new CarManagement();
    carManagement.setVisible(true);
    }
    
    private void openDriverManagement() {
        DriverManagement driverManagement = new DriverManagement();
    driverManagement.setVisible(true);
    }
    
    private void openReports() {
 ReportManagement reportManagement = new ReportManagement();
    reportManagement.setVisible(true);
    }
    
    private void openHelp() {
        
        HelpForm helpForm = new HelpForm();
        helpForm.setVisible(true);

    }
}