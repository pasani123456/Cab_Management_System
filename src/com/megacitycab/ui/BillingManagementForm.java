package com.megacitycab.ui;

import com.megacitycab.controller.BillingController;
import com.megacitycab.model.Bill;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BillingManagementForm extends JFrame {

    private JPanel mainPanel;
    private JTable billsTable;
    private JButton viewBillBtn;
    private JButton markPaidBtn;
    private JButton refreshBtn;

    private BillingController billingController;
    private DefaultTableModel tableModel;

    public BillingManagementForm() {
        billingController = new BillingController();
        initComponents();
        loadBillsTable();
    }

    private void initComponents() {
        // Main Panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Table Setup
        String[] columns = {"Bill #", "Booking #", "Billing Date", "Total Amount", "Payment Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        billsTable = new JTable(tableModel);
        billsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        billsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        billsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        billsTable.getTableHeader().setBackground(new Color(200, 200, 200));
        billsTable.getTableHeader().setForeground(Color.BLACK);
        billsTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(billsTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Billing List"));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        viewBillBtn = createStyledButton("View Bill", new Color(0, 128, 128), Color.WHITE);
        viewBillBtn.addActionListener((ActionEvent e) -> viewBillBtnActionPerformed(e));
        buttonPanel.add(viewBillBtn);

        markPaidBtn = createStyledButton("Mark as Paid", new Color(0, 128, 0), Color.WHITE);
        markPaidBtn.addActionListener((ActionEvent e) -> markPaidBtnActionPerformed(e));
        buttonPanel.add(markPaidBtn);

        refreshBtn = createStyledButton("Refresh", new Color(128, 128, 128), Color.WHITE);
        refreshBtn.addActionListener((ActionEvent e) -> loadBillsTable());
        buttonPanel.add(refreshBtn);

        tablePanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add Components to Main Panel
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Set Up the Frame
        setTitle("Billing Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(900, 600);
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

    private void loadBillsTable() {
        List<Bill> bills = billingController.getAllBills();
        tableModel.setRowCount(0);

        for (Bill bill : bills) {
            tableModel.addRow(new Object[]{
                bill.getBillNumber(),
                bill.getBookingNumber(),
                bill.getBillingDate().toString(),
                String.format("%.2f", bill.getTotalAmount()),
                bill.getPaymentStatus()
            });
        }
    }

    private void viewBillBtnActionPerformed(ActionEvent evt) {
        int selectedRow = billsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a bill to view",
                    "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String billNumber = billsTable.getValueAt(selectedRow, 0).toString();
        Bill bill = billingController.getBillByNumber(billNumber);

        if (bill != null) {
            new BillDetailsDialog(this, true, bill).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Could not find bill details",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void markPaidBtnActionPerformed(ActionEvent evt) {
        int selectedRow = billsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a bill to mark as paid",
                    "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String billNumber = billsTable.getValueAt(selectedRow, 0).toString();
        Bill bill = billingController.getBillByNumber(billNumber);

        if (bill != null) {
            bill.setPaymentStatus("Paid");
            boolean success = billingController.updateBill(bill);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Bill marked as paid successfully",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                loadBillsTable();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to update bill status",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Could not find bill details",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}