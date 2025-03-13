package com.megacitycab.ui;

import com.megacitycab.controller.CustomerController;
import com.megacitycab.model.Customer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CustomerManagement extends JFrame {
    private JPanel mainPanel;
    private JTextField txtRegNumber;
    private JTextField txtName;
    private JTextField txtAddress;
    private JTextField txtNIC;
    private JTextField txtTelephone;
    private JTextField txtSearch;
    private JTable tblCustomers;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnSearch;
    private CustomerController customerController;
    private DefaultTableModel tableModel;

    public CustomerManagement() {
        customerController = new CustomerController();
        initComponents();
        loadCustomers();
    }

    private void initComponents() {
        // Main Panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Customer Details"));
        formPanel.setBackground(Color.WHITE); // White background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Custom Font
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Registration Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblRegNumber = new JLabel("Registration Number:");
        lblRegNumber.setFont(labelFont);
        formPanel.add(lblRegNumber, gbc);
        txtRegNumber = new JTextField(15);
        txtRegNumber.setFont(fieldFont);
        txtRegNumber.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(txtRegNumber, gbc);

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

        // Address
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setFont(labelFont);
        formPanel.add(lblAddress, gbc);
        txtAddress = new JTextField(15);
        txtAddress.setFont(fieldFont);
        txtAddress.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(txtAddress, gbc);

        // NIC
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblNIC = new JLabel("NIC:");
        lblNIC.setFont(labelFont);
        formPanel.add(lblNIC, gbc);
        txtNIC = new JTextField(15);
        txtNIC.setFont(fieldFont);
        txtNIC.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(txtNIC, gbc);

        // Telephone
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblTelephone = new JLabel("Telephone:");
        lblTelephone.setFont(labelFont);
        formPanel.add(lblTelephone, gbc);
        txtTelephone = new JTextField(15);
        txtTelephone.setFont(fieldFont);
        txtTelephone.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(txtTelephone, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        btnAdd = createStyledButton("Add", new Color(0, 128, 0), Color.WHITE);
        btnAdd.addActionListener((ActionEvent e) -> addCustomer());
        buttonPanel.add(btnAdd);

        btnUpdate = createStyledButton("Update", new Color(0, 0, 128), Color.WHITE);
        btnUpdate.addActionListener((ActionEvent e) -> updateCustomer());
        buttonPanel.add(btnUpdate);

        btnDelete = createStyledButton("Delete", new Color(128, 0, 0), Color.WHITE);
        btnDelete.addActionListener((ActionEvent e) -> deleteCustomer());
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
        btnSearch.addActionListener((ActionEvent e) -> searchCustomers());
        searchPanel.add(btnSearch);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBorder(BorderFactory.createTitledBorder("Customer List"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"Reg Number", "Name", "Address", "NIC", "Telephone"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblCustomers = new JTable(tableModel);
        tblCustomers.setFont(fieldFont);
        tblCustomers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCustomers.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblCustomers.getTableHeader().setBackground(new Color(200, 200, 200));
        tblCustomers.getTableHeader().setForeground(Color.BLACK);
        tblCustomers.setRowHeight(25);
        tblCustomers.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectCustomer();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblCustomers);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(searchPanel, BorderLayout.NORTH);

        // Add Components to Main Panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Set Up the Frame
        setTitle("Customer Management");
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

    private void loadCustomers() {
        tableModel.setRowCount(0);
        List<Customer> customers = customerController.getAllCustomers();
        for (Customer customer : customers) {
            Object[] row = {
                customer.getRegNumber(),
                customer.getName(),
                customer.getAddress(),
                customer.getNic(),
                customer.getTelephone()
            };
            tableModel.addRow(row);
        }
    }

    private void searchCustomers() {
        String searchTerm = txtSearch.getText().trim();
        if (searchTerm.isEmpty()) {
            loadCustomers();
            return;
        }
        tableModel.setRowCount(0);
        List<Customer> customers = customerController.searchCustomers(searchTerm);
        for (Customer customer : customers) {
            Object[] row = {
                customer.getRegNumber(),
                customer.getName(),
                customer.getAddress(),
                customer.getNic(),
                customer.getTelephone()
            };
            tableModel.addRow(row);
        }
    }

    private void selectCustomer() {
        int selectedRow = tblCustomers.getSelectedRow();
        if (selectedRow >= 0) {
            String regNumber = (String) tableModel.getValueAt(selectedRow, 0);
            Customer customer = customerController.getCustomerByRegNumber(regNumber);
            if (customer != null) {
                txtRegNumber.setText(customer.getRegNumber());
                txtName.setText(customer.getName());
                txtAddress.setText(customer.getAddress());
                txtNIC.setText(customer.getNic());
                txtTelephone.setText(customer.getTelephone());
                txtRegNumber.setEditable(false);
            }
        }
    }

    private void addCustomer() {
        if (!validateForm()) return;
        Customer customer = new Customer();
        customer.setRegNumber(txtRegNumber.getText().trim());
        customer.setName(txtName.getText().trim());
        customer.setAddress(txtAddress.getText().trim());
        customer.setNic(txtNIC.getText().trim());
        customer.setTelephone(txtTelephone.getText().trim());
        boolean success = customerController.addCustomer(customer);
        if (success) {
            JOptionPane.showMessageDialog(this, "Customer added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadCustomers();
        } else {
            JOptionPane.showMessageDialog(this, "Customer with this registration number already exists", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCustomer() {
        if (!validateForm()) return;
        int selectedRow = tblCustomers.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a customer to update", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String regNumber = (String) tableModel.getValueAt(selectedRow, 0);
        Customer customer = new Customer();
        customer.setRegNumber(regNumber);
        customer.setName(txtName.getText().trim());
        customer.setAddress(txtAddress.getText().trim());
        customer.setNic(txtNIC.getText().trim());
        customer.setTelephone(txtTelephone.getText().trim());
        boolean success = customerController.updateCustomer(regNumber, customer);
        if (success) {
            JOptionPane.showMessageDialog(this, "Customer updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadCustomers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update customer", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCustomer() {
        int selectedRow = tblCustomers.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String regNumber = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this customer?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = customerController.deleteCustomer(regNumber);
            if (success) {
                JOptionPane.showMessageDialog(this, "Customer deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadCustomers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete customer", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        txtRegNumber.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtNIC.setText("");
        txtTelephone.setText("");
        txtRegNumber.setEditable(true);
        tblCustomers.clearSelection();
    }

    private boolean validateForm() {
        String regNumber = txtRegNumber.getText().trim();
        String name = txtName.getText().trim();
        String address = txtAddress.getText().trim();
        String nic = txtNIC.getText().trim();
        String telephone = txtTelephone.getText().trim();
        if (regNumber.isEmpty() || name.isEmpty() || address.isEmpty() || nic.isEmpty() || telephone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!telephone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Telephone number must be 10 digits", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!(nic.matches("\\d{9}[vVxX]") || nic.matches("\\d{12}"))) {
            JOptionPane.showMessageDialog(this, "NIC must be in valid format (9 digits + v/V/x/X or 12 digits)", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerManagement frame = new CustomerManagement();
            frame.setVisible(true);
        });
    }
}