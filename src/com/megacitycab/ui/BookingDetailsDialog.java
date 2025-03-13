package com.megacitycab.ui;

import com.megacitycab.controller.CarController;
import com.megacitycab.controller.CustomerController;
import com.megacitycab.controller.DriverController;
import com.megacitycab.model.Booking;
import com.megacitycab.model.Car;
import com.megacitycab.model.Customer;
import com.megacitycab.model.Driver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;

public class BookingDetailsDialog extends JDialog {
    
    private JPanel mainPanel;
    private JLabel lblBookingNumber;
    private JLabel lblCustomer;
    private JLabel lblPickup;
    private JLabel lblDestination;
    private JLabel lblDateTime;
    private JLabel lblDistance;
    private JLabel lblCar;
    private JLabel lblDriver;
    private JLabel lblStatus;
    
    private JButton btnClose;
    
    private CustomerController customerController;
    private CarController carController;
    private DriverController driverController;
    
    public BookingDetailsDialog(Frame parent, boolean modal, Booking booking) {
        super(parent, modal);
        
        customerController = new CustomerController();
        carController = new CarController();
        driverController = new DriverController();
        
        initComponents();
        populateBookingDetails(booking);
    }
    
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Details panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Booking Details"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Booking Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(new JLabel("Booking Number:"), gbc);
        
        lblBookingNumber = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        detailsPanel.add(lblBookingNumber, gbc);
        
        // Customer
        gbc.gridx = 0;
        gbc.gridy = 1;
        detailsPanel.add(new JLabel("Customer:"), gbc);
        
        lblCustomer = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 1;
        detailsPanel.add(lblCustomer, gbc);
        
        // Pickup
        gbc.gridx = 0;
        gbc.gridy = 2;
        detailsPanel.add(new JLabel("Pickup Location:"), gbc);
        
        lblPickup = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 2;
        detailsPanel.add(lblPickup, gbc);
        
        // Destination
        gbc.gridx = 0;
        gbc.gridy = 3;
        detailsPanel.add(new JLabel("Destination:"), gbc);
        
        lblDestination = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 3;
        detailsPanel.add(lblDestination, gbc);
        
        // Date/Time
        gbc.gridx = 0;
        gbc.gridy = 4;
        detailsPanel.add(new JLabel("Date/Time:"), gbc);
        
        lblDateTime = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 4;
        detailsPanel.add(lblDateTime, gbc);
        
        // Distance
        gbc.gridx = 0;
        gbc.gridy = 5;
        detailsPanel.add(new JLabel("Distance (km):"), gbc);
        
        lblDistance = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 5;
        detailsPanel.add(lblDistance, gbc);
        
        // Car
        gbc.gridx = 0;
        gbc.gridy = 6;
        detailsPanel.add(new JLabel("Car:"), gbc);
        
        lblCar = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 6;
        detailsPanel.add(lblCar, gbc);
        
        // Driver
        gbc.gridx = 0;
        gbc.gridy = 7;
        detailsPanel.add(new JLabel("Driver:"), gbc);
        
        lblDriver = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 7;
        detailsPanel.add(lblDriver, gbc);
        
        // Status
        gbc.gridx = 0;
        gbc.gridy = 8;
        detailsPanel.add(new JLabel("Status:"), gbc);
        
        lblStatus = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 8;
        detailsPanel.add(lblStatus, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        btnClose = new JButton("Close");
        btnClose.addActionListener((ActionEvent e) -> {
            dispose();
        });
        buttonPanel.add(btnClose);
        
        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Set up dialog
        setTitle("Booking Details");
        setContentPane(mainPanel);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void populateBookingDetails(Booking booking) {
        if (booking == null) {
            return;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        // Get related objects
        Customer customer = customerController.getCustomerByRegNumber(booking.getCustomerRegNumber());
        Car car = carController.getCarById(booking.getCarId());
        Driver driver = driverController.getDriverById(booking.getDriverId());
        
        // Populate fields
        lblBookingNumber.setText(booking.getBookingNumber());
        lblCustomer.setText(customer != null ? customer.getName() + " (" + customer.getRegNumber() + ")" : "Unknown");
        lblPickup.setText(booking.getPickupLocation());
        lblDestination.setText(booking.getDestination());
        lblDateTime.setText(booking.getBookingTime().format(formatter));
        lblDistance.setText(String.format("%.2f", booking.getDistance()));
        lblCar.setText(car != null ? car.getModel() + " - " + car.getType() + " (" + car.getCarId() + ")" : "Unknown");
        lblDriver.setText(driver != null ? driver.getName() + " (" + driver.getDriverId() + ")" : "Unknown");
        lblStatus.setText(booking.getStatus());
        
        // Set status color based on booking status
        switch (booking.getStatus()) {
            case "Pending":
                lblStatus.setForeground(Color.ORANGE);
                break;
            case "Confirmed":
                lblStatus.setForeground(Color.BLUE);
                break;
            case "Completed":
                lblStatus.setForeground(Color.GREEN.darker());
                break;
            case "Cancelled":
                lblStatus.setForeground(Color.RED);
                break;
            default:
                lblStatus.setForeground(Color.BLACK);
        }
    }
}