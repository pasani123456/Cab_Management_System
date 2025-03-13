package com.megacitycab.ui;

import com.megacitycab.controller.*;
import com.megacitycab.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class BookingManagementForm extends JFrame {
    private JPanel mainPanel;
    private JPanel cardPanel;
    private JPanel createPanel;
    private JPanel viewPanel;
    private JTextField bookingNumberField;
    private JComboBox<String> customerComboBox;
    private JTextField pickupField;
    private JTextField destinationField;
    private JTextField distanceField;
    private JComboBox<String> carComboBox;
    private JComboBox<String> driverComboBox;
    private JComboBox<String> statusComboBox;
    private JTable bookingsTable;
    private JButton createBookingBtn;
    private JButton newBookingBtn;
    private JButton cancelBtn;
    private JButton viewBookingBtn;
    private JButton updateStatusBtn;
    private JButton generateBillBtn;
    private BookingController bookingController;
    private CustomerController customerController;
    private CarController carController;
    private DriverController driverController;
    private DefaultTableModel tableModel;
    private MainDashboard dashboard;

    public BookingManagementForm() {
        bookingController = new BookingController();
        customerController = new CustomerController();
        carController = new CarController();
        driverController = new DriverController();
        initComponents();
        setupComboBoxes();
        loadBookingsTable();
        setupCardLayout();
    }

    public BookingManagementForm(MainDashboard dashboard) {
        this();
        this.dashboard = dashboard;
    }

    private void setupCardLayout() {
        cardPanel.setLayout(new CardLayout());
        cardPanel.add(createPanel, "create");
        cardPanel.add(viewPanel, "view");
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, "view");
    }

    private void setupComboBoxes() {
        // Load customer combo box
        List<Customer> customers = customerController.getAllCustomers();
        DefaultComboBoxModel<String> customerModel = new DefaultComboBoxModel<>();
        customerModel.addElement("-- Select Customer --");
        for(Customer c : customers) {
            customerModel.addElement(c.getRegNumber() + " - " + c.getName());
        }
        customerComboBox.setModel(customerModel);

        // Load car combo box
        List<Car> cars = carController.getAvailableCars();
        DefaultComboBoxModel<String> carModel = new DefaultComboBoxModel<>();
        carModel.addElement("-- Select Car --");
        for(Car c : cars) {
            carModel.addElement(c.getCarId() + " - " + c.getModel() + " (" + c.getType() + ")");
        }
        carComboBox.setModel(carModel);

        // Load driver combo box
        List<Driver> drivers = driverController.getAvailableDrivers();
        DefaultComboBoxModel<String> driverModel = new DefaultComboBoxModel<>();
        driverModel.addElement("-- Select Driver --");
        for(Driver d : drivers) {
            driverModel.addElement(d.getDriverId() + " - " + d.getName());
        }
        driverComboBox.setModel(driverModel);

        // Setup status combo box
        DefaultComboBoxModel<String> statusModel = new DefaultComboBoxModel<>();
        statusModel.addElement("Pending");
        statusModel.addElement("Confirmed");
        statusModel.addElement("Completed");
        statusModel.addElement("Cancelled");
        statusComboBox.setModel(statusModel);
    }

    private void loadBookingsTable() {
        List<Booking> bookings = bookingController.getAllBookings();
        // Clear existing data
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // Add booking data
        for (Booking booking : bookings) {
            Customer customer = customerController.getCustomerByRegNumber(booking.getCustomerRegNumber());
            String customerName = customer != null ? customer.getName() : "Unknown";
            tableModel.addRow(new Object[]{
                booking.getBookingNumber(),
                customerName,
                booking.getPickupLocation(),
                booking.getDestination(),
                booking.getBookingTime().format(formatter),
                booking.getDistance(),
                booking.getStatus()
            });
        }
    }

    private void clearForm() {
        bookingNumberField.setText("");
        customerComboBox.setSelectedIndex(0);
        pickupField.setText("");
        destinationField.setText("");
        distanceField.setText("");
        carComboBox.setSelectedIndex(0);
        driverComboBox.setSelectedIndex(0);
        statusComboBox.setSelectedIndex(0);
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Card panel for switching between create and view
        cardPanel = new JPanel();

        // Create panel
        createPanel = new JPanel(new BorderLayout(10, 10));
        createPanel.setBackground(Color.WHITE);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Booking Details"));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Booking Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblBookingNumber = new JLabel("Booking Number:");
        lblBookingNumber.setFont(labelFont);
        formPanel.add(lblBookingNumber, gbc);
        bookingNumberField = new JTextField(15);
        bookingNumberField.setFont(fieldFont);
        bookingNumberField.setEditable(false);
        bookingNumberField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(bookingNumberField, gbc);

        // Customer
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblCustomer = new JLabel("Customer:");
        lblCustomer.setFont(labelFont);
        formPanel.add(lblCustomer, gbc);
        customerComboBox = new JComboBox<>();
        customerComboBox.setFont(fieldFont);
        customerComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(customerComboBox, gbc);

        // Pickup Location
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblPickup = new JLabel("Pickup Location:");
        lblPickup.setFont(labelFont);
        formPanel.add(lblPickup, gbc);
        pickupField = new JTextField(15);
        pickupField.setFont(fieldFont);
        pickupField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(pickupField, gbc);

        // Destination
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblDestination = new JLabel("Destination:");
        lblDestination.setFont(labelFont);
        formPanel.add(lblDestination, gbc);
        destinationField = new JTextField(15);
        destinationField.setFont(fieldFont);
        destinationField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(destinationField, gbc);

        // Distance
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblDistance = new JLabel("Distance (km):");
        lblDistance.setFont(labelFont);
        formPanel.add(lblDistance, gbc);
        distanceField = new JTextField(15);
        distanceField.setFont(fieldFont);
        distanceField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(distanceField, gbc);

        // Car
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblCar = new JLabel("Car:");
        lblCar.setFont(labelFont);
        formPanel.add(lblCar, gbc);
        carComboBox = new JComboBox<>();
        carComboBox.setFont(fieldFont);
        carComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(carComboBox, gbc);

        // Driver
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel lblDriver = new JLabel("Driver:");
        lblDriver.setFont(labelFont);
        formPanel.add(lblDriver, gbc);
        driverComboBox = new JComboBox<>();
        driverComboBox.setFont(fieldFont);
        driverComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(driverComboBox, gbc);

        // Status
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(labelFont);
        formPanel.add(lblStatus, gbc);
        statusComboBox = new JComboBox<>();
        statusComboBox.setFont(fieldFont);
        statusComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(statusComboBox, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        createBookingBtn = createStyledButton("Create Booking", new Color(0, 128, 0), Color.WHITE);
        createBookingBtn.addActionListener((ActionEvent e) -> createBookingBtnActionPerformed(e));
        buttonPanel.add(createBookingBtn);

        cancelBtn = createStyledButton("Cancel", new Color(128, 128, 128), Color.WHITE);
        cancelBtn.addActionListener((ActionEvent e) -> cancelBtnActionPerformed(e));
        buttonPanel.add(cancelBtn);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        createPanel.add(formPanel, BorderLayout.CENTER);

        // View panel
        viewPanel = new JPanel(new BorderLayout(10, 10));
        viewPanel.setBackground(Color.WHITE);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBorder(BorderFactory.createTitledBorder("Booking List"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"Booking #", "Customer", "Pickup", "Destination", "Date/Time", "Distance (km)", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookingsTable = new JTable(tableModel);
        bookingsTable.setFont(fieldFont);
        bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        bookingsTable.getTableHeader().setBackground(new Color(200, 200, 200));
        bookingsTable.getTableHeader().setForeground(Color.BLACK);
        bookingsTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel for view
        JPanel viewButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        viewButtonPanel.setBackground(new Color(245, 245, 245));

        newBookingBtn = createStyledButton("New Booking", new Color(0, 0, 128), Color.WHITE);
        newBookingBtn.addActionListener((ActionEvent e) -> newBookingBtnActionPerformed(e));
        viewButtonPanel.add(newBookingBtn);

        viewBookingBtn = createStyledButton("View Details", new Color(0, 128, 128), Color.WHITE);
        viewBookingBtn.addActionListener((ActionEvent e) -> viewBookingBtnActionPerformed(e));
        viewButtonPanel.add(viewBookingBtn);

        updateStatusBtn = createStyledButton("Update Status", new Color(128, 0, 128), Color.WHITE);
        updateStatusBtn.addActionListener((ActionEvent e) -> updateStatusBtnActionPerformed(e));
        viewButtonPanel.add(updateStatusBtn);

        generateBillBtn = createStyledButton("Generate Bill", new Color(128, 0, 0), Color.WHITE);
        generateBillBtn.addActionListener((ActionEvent e) -> generateBillBtnActionPerformed(e));
        viewButtonPanel.add(generateBillBtn);

        tablePanel.add(viewButtonPanel, BorderLayout.SOUTH);

        viewPanel.add(tablePanel, BorderLayout.CENTER);

        // Add panels to card panel
        cardPanel.add(createPanel, "create");
        cardPanel.add(viewPanel, "view");

        mainPanel.add(cardPanel, BorderLayout.CENTER);

        setTitle("Booking Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void createBookingBtnActionPerformed(ActionEvent evt) {
        if (customerComboBox.getSelectedIndex() == 0 ||
            carComboBox.getSelectedIndex() == 0 ||
            driverComboBox.getSelectedIndex() == 0 ||
            pickupField.getText().trim().isEmpty() ||
            destinationField.getText().trim().isEmpty() ||
            distanceField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            double distance = Double.parseDouble(distanceField.getText());
            // Create new booking
            Booking booking = new Booking();
            booking.setPickupLocation(pickupField.getText().trim());
            booking.setDestination(destinationField.getText().trim());
            booking.setDistance(distance);
            booking.setBookingTime(LocalDateTime.now());
            booking.setStatus(statusComboBox.getSelectedItem().toString());

            // Extract IDs from combo boxes
            String customerSelection = customerComboBox.getSelectedItem().toString();
            String carSelection = carComboBox.getSelectedItem().toString();
            String driverSelection = driverComboBox.getSelectedItem().toString();
            String customerRegNumber = customerSelection.split(" - ")[0];
            String carId = carSelection.split(" - ")[0];
            String driverId = driverSelection.split(" - ")[0];
            booking.setCustomerRegNumber(customerRegNumber);
            booking.setCarId(carId);
            booking.setDriverId(driverId);

            boolean success = bookingController.createBooking(booking);
            if (success) {
                JOptionPane.showMessageDialog(this, "Booking created successfully\n" +
                        "Booking Number: " + booking.getBookingNumber(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadBookingsTable();
                CardLayout cl = (CardLayout)(cardPanel.getLayout());
                cl.show(cardPanel, "view");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create booking", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Distance must be a valid number", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void newBookingBtnActionPerformed(ActionEvent evt) {
        clearForm();
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, "create");
    }

    private void cancelBtnActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, "view");
    }

    private void viewBookingBtnActionPerformed(ActionEvent evt) {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to view", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String bookingNumber = bookingsTable.getValueAt(selectedRow, 0).toString();
        Booking booking = bookingController.getBookingByNumber(bookingNumber);
        if (booking != null) {
            new BookingDetailsDialog(dashboard, true, booking).setVisible(true);
            loadBookingsTable();
        } else {
            JOptionPane.showMessageDialog(this, "Could not find booking details", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStatusBtnActionPerformed(ActionEvent evt) {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to update", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String bookingNumber = bookingsTable.getValueAt(selectedRow, 0).toString();
        Booking booking = bookingController.getBookingByNumber(bookingNumber);
        if (booking != null) {
            String[] options = {"Pending", "Confirmed", "Completed", "Cancelled"};
            String newStatus = (String) JOptionPane.showInputDialog(this,
                    "Select new status for Booking #" + bookingNumber,
                    "Update Booking Status",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    booking.getStatus());
            if (newStatus != null && !newStatus.equals(booking.getStatus())) {
                booking.setStatus(newStatus);
                boolean success = bookingController.updateBooking(booking);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Booking status updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadBookingsTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update booking status", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Could not find booking details", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double calculateFare(double distance, String carType) {
        double baseFare = 50.0; // Base fare
        double ratePerKm;
        switch (carType.toLowerCase()) {
            case "economy":
                ratePerKm = 10.0;
                break;
            case "luxury":
                ratePerKm = 20.0;
                break;
            case "suv":
                ratePerKm = 15.0;
                break;
            default:
                ratePerKm = 12.0;
                break;
        }
        return baseFare + (distance * ratePerKm);
    }

    private void generateBillBtnActionPerformed(ActionEvent evt) {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to generate bill", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String bookingNumber = bookingsTable.getValueAt(selectedRow, 0).toString();
        String status = bookingsTable.getValueAt(selectedRow, 6).toString();
        if (!status.equals("Completed")) {
            JOptionPane.showMessageDialog(this, "Can only generate bills for completed bookings", "Invalid Status", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Booking booking = bookingController.getBookingByNumber(bookingNumber);
        if (booking == null) {
            JOptionPane.showMessageDialog(this, "Booking not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get car details
        Car car = carController.getCarById(booking.getCarId());
        if (car == null) {
            JOptionPane.showMessageDialog(this, "Car details not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate fare
        double fare = calculateFare(booking.getDistance(), car.getType());

        // Create a dialog to input tax and discount
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        JTextField fareField = new JTextField(String.format("%.2f", fare));
        fareField.setEditable(false);
        JTextField taxField = new JTextField("10.0"); // Default tax
        JTextField discountField = new JTextField("5.0"); // Default discount
        JTextField totalField = new JTextField();
        totalField.setEditable(false);

        // Calculate initial total
        double tax = Double.parseDouble(taxField.getText());
        double discount = Double.parseDouble(discountField.getText());
        double total = fare + (fare * (tax / 100)) - (fare * (discount / 100));
        totalField.setText(String.format("%.2f", total));

        // Add listeners to update total when tax or discount changes
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTotal();
            }

            private void updateTotal() {
                try {
                    double tax = Double.parseDouble(taxField.getText());
                    double discount = Double.parseDouble(discountField.getText());
                    double total = fare + (fare * (tax / 100)) - (fare * (discount / 100));
                    totalField.setText(String.format("%.2f", total));
                } catch (NumberFormatException ex) {
                    totalField.setText("Invalid input");
                }
            }
        };

        taxField.getDocument().addDocumentListener(documentListener);
        discountField.getDocument().addDocumentListener(documentListener);

        panel.add(new JLabel("Fare:"));
        panel.add(fareField);
        panel.add(new JLabel("Tax (%):"));
        panel.add(taxField);
        panel.add(new JLabel("Discount (%):"));
        panel.add(discountField);
        panel.add(new JLabel("Total Amount:"));
        panel.add(totalField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Generate Bill",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                double taxnew = Double.parseDouble(taxField.getText());
                double discountnew = Double.parseDouble(discountField.getText());

                // Create bill
                Bill bill = new Bill(
                        "BILL-" + System.currentTimeMillis(), // Generate unique bill number
                        bookingNumber,
                        LocalDateTime.now(),
                        fare,
                        taxnew,
                        discountnew
                );

                // Save bill to file
                BillingController billingController = new BillingController();
                boolean success = billingController.createBill(bill);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Bill generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to generate bill", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tax and discount must be valid numbers", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void showPanel(String panelName, String bookingNumber) {
        if (panelName.equals("create")) {
            clearForm();
            CardLayout cl = (CardLayout)(cardPanel.getLayout());
            cl.show(cardPanel, "create");
        } else if (panelName.equals("view")) {
            CardLayout cl = (CardLayout)(cardPanel.getLayout());
            cl.show(cardPanel, "view");
        }
    }
}