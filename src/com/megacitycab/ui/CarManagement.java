package com.megacitycab.ui;

import com.megacitycab.controller.CarController;
import com.megacitycab.model.Car;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.UUID;

public class CarManagement extends JFrame {

    private JPanel mainPanel;
    private JTextField txtCarId;
    private JTextField txtModel;
    private JTextField txtPlateNumber;
    private JComboBox<String> cmbType;
    private JCheckBox chkAvailable;
    private JTextField txtSearch;
    private JTable tblCars;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnSearch;

    private CarController carController;
    private DefaultTableModel tableModel;

    public CarManagement() {
        carController = new CarController();
        initComponents();
        loadCars();
    }

    private void initComponents() {
        // Main Panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Car Details"));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Car ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblCarId = new JLabel("Car ID:");
        lblCarId.setFont(labelFont);
        formPanel.add(lblCarId, gbc);
        txtCarId = new JTextField(15);
        txtCarId.setFont(fieldFont);
        txtCarId.setEditable(false); // Generated automatically
        txtCarId.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(txtCarId, gbc);

        // Model
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblModel = new JLabel("Model:");
        lblModel.setFont(labelFont);
        formPanel.add(lblModel, gbc);
        txtModel = new JTextField(15);
        txtModel.setFont(fieldFont);
        txtModel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(txtModel, gbc);

        // Plate Number
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblPlateNumber = new JLabel("Plate Number:");
        lblPlateNumber.setFont(labelFont);
        formPanel.add(lblPlateNumber, gbc);
        txtPlateNumber = new JTextField(15);
        txtPlateNumber.setFont(fieldFont);
        txtPlateNumber.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(txtPlateNumber, gbc);

        // Type
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblType = new JLabel("Type:");
        lblType.setFont(labelFont);
        formPanel.add(lblType, gbc);
        cmbType = new JComboBox<>(new String[]{"Economy", "Standard", "Premium"});
        cmbType.setFont(fieldFont);
        cmbType.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(cmbType, gbc);

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
        btnAdd.addActionListener((ActionEvent e) -> addCar());
        buttonPanel.add(btnAdd);

        btnUpdate = createStyledButton("Update", new Color(0, 0, 128), Color.WHITE);
        btnUpdate.addActionListener((ActionEvent e) -> updateCar());
        buttonPanel.add(btnUpdate);

        btnDelete = createStyledButton("Delete", new Color(128, 0, 0), Color.WHITE);
        btnDelete.addActionListener((ActionEvent e) -> deleteCar());
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
        btnSearch.addActionListener((ActionEvent e) -> searchCars());
        searchPanel.add(btnSearch);

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        filterPanel.setBackground(new Color(245, 245, 245));
        JCheckBox chkShowAvailable = new JCheckBox("Show only available cars");
        chkShowAvailable.setFont(labelFont);
        chkShowAvailable.addActionListener((ActionEvent e) -> {
            if (chkShowAvailable.isSelected()) {
                loadAvailableCars();
            } else {
                loadCars();
            }
        });
        filterPanel.add(chkShowAvailable);

        JPanel topTablePanel = new JPanel(new BorderLayout());
        topTablePanel.add(searchPanel, BorderLayout.WEST);
        topTablePanel.add(filterPanel, BorderLayout.EAST);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Car List"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"Car ID", "Model", "Plate Number", "Type", "Available"};
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

        tblCars = new JTable(tableModel);
        tblCars.setFont(fieldFont);
        tblCars.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCars.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblCars.getTableHeader().setBackground(new Color(200, 200, 200));
        tblCars.getTableHeader().setForeground(Color.BLACK);
        tblCars.setRowHeight(25);
        tblCars.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectCar();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblCars);
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
        setTitle("Car Management");
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

    private void loadCars() {
        tableModel.setRowCount(0);
        List<Car> cars = carController.getAllCars();
        for (Car car : cars) {
            Object[] row = {
                car.getCarId(),
                car.getModel(),
                car.getPlateNumber(),
                car.getType(),
                car.isAvailable()
            };
            tableModel.addRow(row);
        }
    }

    private void loadAvailableCars() {
        tableModel.setRowCount(0);
        List<Car> cars = carController.getAvailableCars();
        for (Car car : cars) {
            Object[] row = {
                car.getCarId(),
                car.getModel(),
                car.getPlateNumber(),
                car.getType(),
                car.isAvailable()
            };
            tableModel.addRow(row);
        }
    }

    private void searchCars() {
        String searchTerm = txtSearch.getText().trim().toLowerCase();
        if (searchTerm.isEmpty()) {
            loadCars();
            return;
        }
        tableModel.setRowCount(0);
        List<Car> cars = carController.getAllCars();
        for (Car car : cars) {
            if (car.getModel().toLowerCase().contains(searchTerm) ||
                car.getPlateNumber().toLowerCase().contains(searchTerm) ||
                car.getType().toLowerCase().contains(searchTerm) ||
                car.getCarId().toLowerCase().contains(searchTerm)) {
                Object[] row = {
                    car.getCarId(),
                    car.getModel(),
                    car.getPlateNumber(),
                    car.getType(),
                    car.isAvailable()
                };
                tableModel.addRow(row);
            }
        }
    }

    private void selectCar() {
        int selectedRow = tblCars.getSelectedRow();
        if (selectedRow >= 0) {
            String carId = (String) tableModel.getValueAt(selectedRow, 0);
            Car car = carController.getCarById(carId);
            if (car != null) {
                txtCarId.setText(car.getCarId());
                txtModel.setText(car.getModel());
                txtPlateNumber.setText(car.getPlateNumber());
                cmbType.setSelectedItem(car.getType());
                chkAvailable.setSelected(car.isAvailable());
                txtCarId.setEditable(false);
            }
        }
    }

    private void addCar() {
        if (!validateForm()) return;

        String carId = "CAR" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        txtCarId.setText(carId);

        Car car = new Car();
        car.setCarId(carId);
        car.setModel(txtModel.getText().trim());
        car.setPlateNumber(txtPlateNumber.getText().trim());
        car.setType((String) cmbType.getSelectedItem());
        car.setAvailable(chkAvailable.isSelected());

        boolean success = carController.createCar(car);
        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Car added successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadCars();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to add car",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCar() {
        if (!validateForm()) return;

        int selectedRow = tblCars.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a car to update",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String carId = txtCarId.getText().trim();
        Car car = new Car();
        car.setCarId(carId);
        car.setModel(txtModel.getText().trim());
        car.setPlateNumber(txtPlateNumber.getText().trim());
        car.setType((String) cmbType.getSelectedItem());
        car.setAvailable(chkAvailable.isSelected());

        boolean success = carController.updateCar(car);
        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Car updated successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadCars();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to update car",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCar() {
        int selectedRow = tblCars.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a car to delete",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String carId = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this car?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = carController.deleteCar(carId);
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Car deleted successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadCars();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to delete car",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        txtCarId.setText("");
        txtModel.setText("");
        txtPlateNumber.setText("");
        cmbType.setSelectedIndex(0);
        chkAvailable.setSelected(true);
        txtCarId.setEditable(true);
        tblCars.clearSelection();
    }

    private boolean validateForm() {
        String model = txtModel.getText().trim();
        String plateNumber = txtPlateNumber.getText().trim();

        if (model.isEmpty() || plateNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Model and Plate Number are required",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!plateNumber.matches("[A-Z]{2,3}-\\d{4}")) {
            JOptionPane.showMessageDialog(this,
                    "Plate Number must be in format XX-1234 or XXX-1234",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}