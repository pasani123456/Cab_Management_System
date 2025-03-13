package com.megacitycab.ui;

import javax.swing.*;
import java.awt.*;

public class HelpForm extends JFrame {

    public HelpForm() {
        initComponents();
        setTitle("Application Help");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Booking Help
        JPanel bookingHelpPanel = createHelpPanel("""
            **Booking Management:**
            1. Add a new booking by filling in the customer details, pickup location, destination, and other required fields.
            2. Edit an existing booking by selecting it from the list and updating the details.
            3. Delete a booking by selecting it and clicking the "Delete" button.
            4. View all bookings in the table.
            5. Use the search bar to filter bookings by customer name, pickup location, or destination.
            """);

        // Billing Help
        JPanel billingHelpPanel = createHelpPanel("""
            **Billing Management:**
            1. Generate a bill for a completed booking.
            2. Enter the fare, tax, and discount details.
            3. View all bills in the table.
            4. Export bills to CSV for record-keeping.
            """);

        // Drivers Help
        JPanel driversHelpPanel = createHelpPanel("""
            **Driver Management:**
            1. Add a new driver by entering their name, license number, and contact details.
            2. Edit an existing driver's details by selecting them from the list.
            3. Delete a driver by selecting them and clicking the "Delete" button.
            4. View all drivers in the table.
            5. Assign drivers to bookings based on availability.
            """);

        // Cars Help
        JPanel carsHelpPanel = createHelpPanel("""
            **Car Management:**
            1. Add a new car by entering its model, plate number, and type (Economy, Standard, Premium).
            2. Edit an existing car's details by selecting it from the list.
            3. Delete a car by selecting it and clicking the "Delete" button.
            4. View all cars in the table.
            5. Assign cars to bookings based on availability and type.
            """);

        // Reports Help
        JPanel reportsHelpPanel = createHelpPanel("""
            **Report Management:**
            1. Select the report type (Daily Revenue, Monthly Revenue).
            2. Enter the start and end dates in the format yyyy-MM-dd.
            3. Click "Generate Report" to view the data.
            4. Use "Export to CSV" to save the report as a CSV file.
            5. View charts for visual representation of revenue data.
            """);

        // Customers Help
        JPanel customersHelpPanel = createHelpPanel("""
            **Customer Management:**
            1. Add a new customer by entering their name, address, NIC, and contact details.
            2. Edit an existing customer's details by selecting them from the list.
            3. Delete a customer by selecting them and clicking the "Delete" button.
            4. View all customers in the table.
            5. Use the search bar to filter customers by name or NIC.
            """);

        // Add tabs to the tabbed pane
        tabbedPane.addTab("Booking", bookingHelpPanel);
        tabbedPane.addTab("Billing", billingHelpPanel);
        tabbedPane.addTab("Drivers", driversHelpPanel);
        tabbedPane.addTab("Cars", carsHelpPanel);
        tabbedPane.addTab("Reports", reportsHelpPanel);
        tabbedPane.addTab("Customers", customersHelpPanel);

        // Close Button
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> dispose()); // Close the form

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnClose);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    // Helper method to create a help panel with text
    private JPanel createHelpPanel(String helpText) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea helpTextArea = new JTextArea(helpText);
        helpTextArea.setEditable(false);
        helpTextArea.setLineWrap(true);
        helpTextArea.setWrapStyleWord(true);
        helpTextArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(helpTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HelpForm().setVisible(true);
        });
    }
}