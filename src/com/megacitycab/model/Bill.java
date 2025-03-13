package com.megacitycab.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bill {
    private String billNumber;
    private String bookingNumber;
    private LocalDateTime billingDate;
    private double fare;
    private double tax; // in percentage
    private double discount; // in percentage
    private double totalAmount;
    private String paymentStatus; // "Paid", "Pending"
    
    
    public Bill(){
        
    }
    
    public Bill(String billNumber, String bookingNumber, LocalDateTime billingDate, 
               double fare, double tax, double discount) {
        this.billNumber = billNumber;
        this.bookingNumber = bookingNumber;
        this.billingDate = billingDate;
        this.fare = fare;
        this.tax = tax;
        this.discount = discount;
        this.calculateTotal();
        this.paymentStatus = "Pending";
    }
    
    // Calculate total amount including tax and discount
    private void calculateTotal() {
        double taxAmount = fare * (tax / 100);
        double discountAmount = fare * (discount / 100);
        this.totalAmount = fare + taxAmount - discountAmount;
    }
    
    // Getters and Setters
    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public LocalDateTime getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDateTime billingDate) {
        this.billingDate = billingDate;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
        this.calculateTotal();
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
        this.calculateTotal();
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        this.calculateTotal();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return billNumber + "," + bookingNumber + "," + billingDate.format(formatter) + "," + 
               fare + "," + tax + "," + discount + "," + totalAmount + "," + paymentStatus;
    }
}