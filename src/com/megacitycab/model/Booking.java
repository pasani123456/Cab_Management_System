package com.megacitycab.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {
    private String bookingNumber;
    private String customerRegNumber;
    private String pickupLocation;
    private String destination;
    private LocalDateTime bookingTime;
    private String carId;
    private String driverId;
    private double distance; // in kilometers
    private String status; // "Pending", "Confirmed", "Completed", "Cancelled"
    
    public Booking(){
        
    }
    
    public Booking(String bookingNumber, String customerRegNumber, String pickupLocation, 
                  String destination, LocalDateTime bookingTime, String carId, 
                  String driverId, double distance, String status) {
        this.bookingNumber = bookingNumber;
        this.customerRegNumber = customerRegNumber;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.bookingTime = bookingTime;
        this.carId = carId;
        this.driverId = driverId;
        this.distance = distance;
        this.status = status;
    }
    
    // Calculate fare based on distance and a base rate
    public double calculateFare() {
        double baseRate = 50.0; // Base fare in rupees
        double perKmRate = 40.0; // Rate per kilometer
        return baseRate + (distance * perKmRate);
    }
    
    // Getters and Setters
    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getCustomerRegNumber() {
        return customerRegNumber;
    }

    public void setCustomerRegNumber(String customerRegNumber) {
        this.customerRegNumber = customerRegNumber;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return bookingNumber + "," + customerRegNumber + "," + pickupLocation + "," + 
               destination + "," + bookingTime.format(formatter) + "," + 
               carId + "," + driverId + "," + distance + "," + status;
    }
}