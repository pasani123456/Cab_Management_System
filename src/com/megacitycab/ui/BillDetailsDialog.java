package com.megacitycab.ui;

import com.megacitycab.model.Bill;
import javax.swing.*;
import java.awt.*;

public class BillDetailsDialog extends JDialog {

    private JLabel lblBillNumber;
    private JLabel lblBookingNumber;
    private JLabel lblBillingDate;
    private JLabel lblFare;
    private JLabel lblTax;
    private JLabel lblDiscount;
    private JLabel lblTotalAmount;
    private JLabel lblPaymentStatus;

    public BillDetailsDialog(JFrame parent, boolean modal, Bill bill) {
        super(parent, modal);
        initComponents();
        populateBillDetails(bill);
    }

    private void initComponents() {
        setTitle("Bill Details");
        setLayout(new GridLayout(8, 2, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(getParent());

        add(new JLabel("Bill Number:"));
        lblBillNumber = new JLabel();
        add(lblBillNumber);

        add(new JLabel("Booking Number:"));
        lblBookingNumber = new JLabel();
        add(lblBookingNumber);

        add(new JLabel("Billing Date:"));
        lblBillingDate = new JLabel();
        add(lblBillingDate);

        add(new JLabel("Fare:"));
        lblFare = new JLabel();
        add(lblFare);

        add(new JLabel("Tax:"));
        lblTax = new JLabel();
        add(lblTax);

        add(new JLabel("Discount:"));
        lblDiscount = new JLabel();
        add(lblDiscount);

        add(new JLabel("Total Amount:"));
        lblTotalAmount = new JLabel();
        add(lblTotalAmount);

        add(new JLabel("Payment Status:"));
        lblPaymentStatus = new JLabel();
        add(lblPaymentStatus);
    }

    private void populateBillDetails(Bill bill) {
        lblBillNumber.setText(bill.getBillNumber());
        lblBookingNumber.setText(bill.getBookingNumber());
        lblBillingDate.setText(bill.getBillingDate().toString());
        lblFare.setText(String.format("$%.2f", bill.getFare()));
        lblTax.setText(String.format("%.2f%%", bill.getTax()));
        lblDiscount.setText(String.format("%.2f%%", bill.getDiscount()));
        lblTotalAmount.setText(String.format("$%.2f", bill.getTotalAmount()));
        lblPaymentStatus.setText(bill.getPaymentStatus());
    }
}