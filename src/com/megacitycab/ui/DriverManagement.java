package com.megacitycab.ui;

import com.megacitycab.controller.DriverController;
import com.megacitycab.model.Driver;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.UUID;

public class DriverManagement extends JFrame {

    private JPanel mainPanel;
    private JTextField txtDriverId;
    private JTextField txtName;
    private JTextField txtLicenseNumber;
    private JTextField txtTelephone;
    private JCheckBox chkAvailable;
    private JTextField txtSearch;
    private JTable tblDrivers;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnSearch;

    private DriverController driverController;
    private DefaultTableModel tableModel;

    public DriverManagement() {
        driverController = new DriverController();
        initComponents();
        loadDrivers();
    }

    private void initComponents() {
        // Main Panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Driver Details"));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Driver ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblDriverId = new JLabel("Driver ID:");
        lblDriverId.setFont(labelFont);
        formPanel.add(lblDriverId, gbc);
        txtDriverId = new JTextField(15);
        txtDriverId.setFont(fieldFont);
        txtDriverId.setEditable(false); // Generated automatically
        txtDriverId.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(txtDriverId, gbc);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(labelFont);
        formPanel.add(lblName, gbc);
        txtName = new JTextField(15);
        txtName.setFont(fieldFont);
        txtName.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(txtName, gbc);

        // License Number
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblLicenseNumber = new JLabel("License Number:");
        lblLicenseNumber.setFont(labelFont);
        formPanel.add(lblLicenseNumber, gbc);
        txtLicenseNumber = new JTextField(15);
        txtLicenseNumber.setFont(fieldFont);
        txtLicenseNumber.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(txtLicenseNumber, gbc);

        // Telephone
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblTelephone = new JLabel("Telephone:");
        lblTelephone.setFont(labelFont);
        formPanel.add(lblTelephone, gbc);
        txtTelephone = new JTextField(15);
        txtTelephone.setFont(fieldFont);
        txtTelephone.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(txtTelephone, gbc);

        // Available
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblAvailable = new JLabel("Available:");
        lblAvailable.setFont(labelFont);
        formPanel.add(lblAvailable, gbc);
        chkAvailable = new JCheckBox();
        chkAvailable.setSelected(true);
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(chkAvailable, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        btnAdd = createStyledButton("Add", new Color(0, 128, 0), Color.WHITE);
        btnAdd.addActionListener((ActionEvent e) -> addDriver());
        buttonPanel.add(btnAdd);

        btnUpdate = createStyledButton("Update", new Color(0, 0, 128), Color.WHITE);
        btnUpdate.addActionListener((ActionEvent e) -> updateDriver());
        buttonPanel.add(btnUpdate);

        btnDelete = createStyledButton("Delete", new Color(128, 0, 0), Color.WHITE);
        btnDelete.addActionListener((ActionEvent e) -> deleteDriver());
        buttonPanel.add(btnDelete);

        btnClear = createStyledButton("Clear", new Color(128, 128, 128), Color.WHITE);
        btnClear.addActionListener((ActionEvent e) -> clearForm());
        buttonPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(new Color(245, 245, 245));
        searchPanel.add(new JLabel("Search:"));
        txtSearch = new JTextField(20);
        txtSearch.setFont(fieldFont);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        searchPanel.add(txtSearch);

        btnSearch = createStyledButton("Search", new Color(0, 128, 128), Color.WHITE);
        btnSearch.addActionListener((ActionEvent e) -> searchDrivers());
        searchPanel.add(btnSearch);

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        filterPanel.setBackground(new Color(245, 245, 245));
        JCheckBox chkShowAvailable = new JCheckBox("Show only available drivers");
        chkShowAvailable.setFont(labelFont);
        chkShowAvailable.addActionListener((ActionEvent e) -> {
            if (chkShowAvailable.isSelected()) {
                loadAvailableDrivers();
            } else {
                loadDrivers();
            }
        });
        filterPanel.add(chkShowAvailable);

        JPanel topTablePanel = new JPanel(new BorderLayout());
        topTablePanel.add(searchPanel, BorderLayout.WEST);
        topTablePanel.add(filterPanel, BorderLayout.EAST);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Driver List"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"Driver ID", "Name", "License Number", "Telephone", "Available"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) {
                    return Boolean.class; // For the Available column
                }
                return super.getColumnClass(columnIndex);
            }
        };

        tblDrivers = new JTable(tableModel);
        tblDrivers.setFont(fieldFont);
        tblDrivers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblDrivers.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblDrivers.getTableHeader().setBackground(new Color(200, 200, 200));
        tblDrivers.getTableHeader().setForeground(Color.BLACK);
        tblDrivers.setRowHeight(25);
        tblDrivers.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectDriver();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblDrivers);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(topTablePanel, BorderLayout.NORTH);

        // Add Components to Main Panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Set Up the Frame
        setTitle("Driver Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(1000, 700);
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

    private void loadDrivers() {
        tableModel.setRowCount(0);
        List<Driver> drivers = driverController.getAllDrivers();
        for (Driver driver : drivers) {
            Object[] row = {
                driver.getDriverId(),
                driver.getName(),
                driver.getLicenseNumber(),
                driver.getTelephone(),
                driver.isAvailable()
            };
            tableModel.addRow(row);
        }
    }

    private void loadAvailableDrivers() {
        tableModel.setRowCount(0);
        List<Driver> drivers = driverController.getAvailableDrivers();
        for (Driver driver : drivers) {
            Object[] row = {
                driver.getDriverId(),
                driver.getName(),
                driver.getLicenseNumber(),
                driver.getTelephone(),
                driver.isAvailable()
            };
            tableModel.addRow(row);
        }
    }

    private void searchDrivers() {
        String searchTerm = txtSearch.getText().trim().toLowerCase();
        if (searchTerm.isEmpty()) {
            loadDrivers();
            return;
        }
        tableModel.setRowCount(0);
        List<Driver> drivers = driverController.getAllDrivers();
        for (Driver driver : drivers) {
            if (driver.getName().toLowerCase().contains(searchTerm) ||
                driver.getLicenseNumber().toLowerCase().contains(searchTerm) ||
                driver.getTelephone().toLowerCase().contains(searchTerm) ||
                driver.getDriverId().toLowerCase().contains(searchTerm)) {
                Object[] row = {
                    driver.getDriverId(),
                    driver.getName(),
                    driver.getLicenseNumber(),
                    driver.getTelephone(),
                    driver.isAvailable()
                };
                tableModel.addRow(row);
            }
        }
    }

    private void selectDriver() {
        int selectedRow = tblDrivers.getSelectedRow();
        if (selectedRow >= 0) {
            String driverId = (String) tableModel.getValueAt(selectedRow, 0);
            Driver driver = driverController.getDriverById(driverId);
            if (driver != null) {
                txtDriverId.setText(driver.getDriverId());
                txtName.setText(driver.getName());
                txtLicenseNumber.setText(driver.getLicenseNumber());
                txtTelephone.setText(driver.getTelephone());
                chkAvailable.setSelected(driver.isAvailable());
                txtDriverId.setEditable(false);
            }
        }
    }

    private void addDriver() {
        if (!validateForm()) return;

        String driverId = "DRV" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        txtDriverId.setText(driverId);

        Driver driver = new Driver(
            driverId,
            txtName.getText().trim(),
            txtLicenseNumber.getText().trim(),
            txtTelephone.getText().trim(),
            chkAvailable.isSelected()
        );

        boolean success = driverController.createDriver(driver);
        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Driver added successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadDrivers();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to add driver",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDriver() {
        if (!validateForm()) return;

        int selectedRow = tblDrivers.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a driver to update",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String driverId = txtDriverId.getText().trim();
        Driver driver = new Driver(
            driverId,
            txtName.getText().trim(),
            txtLicenseNumber.getText().trim(),
            txtTelephone.getText().trim(),
            chkAvailable.isSelected()
        );

        boolean success = driverController.updateDriver(driver);
        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Driver updated successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadDrivers();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to update driver",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDriver() {
        int selectedRow = tblDrivers.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a driver to delete",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String driverId = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this driver?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = driverController.deleteDriver(driverId);
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Driver deleted successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadDrivers();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to delete driver",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        txtDriverId.setText("");
        txtName.setText("");
        txtLicenseNumber.setText("");
        txtTelephone.setText("");
        chkAvailable.setSelected(true);
        txtDriverId.setEditable(true);
        tblDrivers.clearSelection();
    }

    private boolean validateForm() {
        String name = txtName.getText().trim();
        String licenseNumber = txtLicenseNumber.getText().trim();
        String telephone = txtTelephone.getText().trim();

        if (name.isEmpty() || licenseNumber.isEmpty() || telephone.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "All fields are required",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!licenseNumber.matches("\\d{6}")) {
            JOptionPane.showMessageDialog(this,
                    "License Number must be exactly 6 digits",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!telephone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this,
                    "Telephone number must be 10 digits",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}