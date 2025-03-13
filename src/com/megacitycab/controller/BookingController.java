package com.megacitycab.controller;

import com.megacitycab.data.FileHandler;
import com.megacitycab.model.Booking;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingController {
    private static final String BOOKING_FILE = "data/bookings.txt";
    private static final String DELIMITER = "|||";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public boolean createBooking(Booking booking) {
        try {
            // Generate booking number if not provided
            if (booking.getBookingNumber() == null || booking.getBookingNumber().isEmpty()) {
                booking.setBookingNumber("BK" + UUID.randomUUID().toString().substring(0, 8));
            }
            
            // Set booking time if not set
            if (booking.getBookingTime() == null) {
                booking.setBookingTime(LocalDateTime.now());
            }
            
            // Convert booking to string and save to file
            String bookingData = bookingToString(booking);
            FileHandler.writeToFile(BOOKING_FILE, bookingData);
            
            // Update car and driver availability if booking is confirmed
            if (booking.getStatus().equals("Confirmed")) {
                CarController carController = new CarController();
                DriverController driverController = new DriverController();
                
                carController.updateCarAvailability(booking.getCarId(), false);
                driverController.updateDriverAvailability(booking.getDriverId(), false);
            }
            
            return true;
        } catch (Exception e) {
            System.err.println("Error creating booking: " + e.getMessage());
            return false;
        }
    }
    
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile(BOOKING_FILE);
        
        for (String line : lines) {
            Booking booking = stringToBooking(line);
            if (booking != null) {
                bookings.add(booking);
            }
        }
        
        return bookings;
    }
    
    public Booking getBookingByNumber(String bookingNumber) {
        List<Booking> bookings = getAllBookings();
        for (Booking booking : bookings) {
            if (booking.getBookingNumber().equals(bookingNumber)) {
                return booking;
            }
        }
        return null;
    }
    
    public List<Booking> getBookingsByCustomer(String customerRegNumber) {
        List<Booking> bookings = getAllBookings();
        List<Booking> customerBookings = new ArrayList<>();
        
        for (Booking booking : bookings) {
            if (booking.getCustomerRegNumber().equals(customerRegNumber)) {
                customerBookings.add(booking);
            }
        }
        
        return customerBookings;
    }
    
    public boolean updateBooking(Booking booking) {
        List<Booking> bookings = getAllBookings();
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        
        for (Booking b : bookings) {
            if (b.getBookingNumber().equals(booking.getBookingNumber())) {
                // Handle car and driver availability based on status change
                if (!b.getStatus().equals(booking.getStatus())) {
                    handleStatusChange(b, booking);
                }
                
                updatedLines.add(bookingToString(booking));
                found = true;
            } else {
                updatedLines.add(bookingToString(b));
            }
        }
        
        if (found) {
            FileHandler.writeToFile(BOOKING_FILE, updatedLines);
            return true;
        }
        return false;
    }
    
    private void handleStatusChange(Booking oldBooking, Booking newBooking) {
        CarController carController = new CarController();
        DriverController driverController = new DriverController();
        
        // If status changed to Confirmed, mark car and driver as unavailable
        if (newBooking.getStatus().equals("Confirmed")) {
            carController.updateCarAvailability(newBooking.getCarId(), false);
            driverController.updateDriverAvailability(newBooking.getDriverId(), false);
        }
        
        // If status changed to Completed or Cancelled, mark car and driver as available
        if ((oldBooking.getStatus().equals("Confirmed") || oldBooking.getStatus().equals("Pending")) &&
            (newBooking.getStatus().equals("Completed") || newBooking.getStatus().equals("Cancelled"))) {
            carController.updateCarAvailability(newBooking.getCarId(), true);
            driverController.updateDriverAvailability(newBooking.getDriverId(), true);
        }
    }
    
    public boolean deleteBooking(String bookingNumber) {
        Booking booking = getBookingByNumber(bookingNumber);
        if (booking == null) {
            return false;
        }
        
        List<Booking> bookings = getAllBookings();
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        
        for (Booking b : bookings) {
            if (!b.getBookingNumber().equals(bookingNumber)) {
                updatedLines.add(bookingToString(b));
            } else {
                found = true;
                
                // If booking is confirmed, make car and driver available again
                if (b.getStatus().equals("Confirmed")) {
                    CarController carController = new CarController();
                    DriverController driverController = new DriverController();
                    
                    carController.updateCarAvailability(b.getCarId(), true);
                    driverController.updateDriverAvailability(b.getDriverId(), true);
                }
            }
        }
        
        if (found) {
            FileHandler.writeToFile(BOOKING_FILE, updatedLines);
            return true;
        }
        return false;
    }
    
    private String bookingToString(Booking booking) {
        return booking.getBookingNumber() + DELIMITER +
               booking.getCustomerRegNumber() + DELIMITER +
               booking.getPickupLocation() + DELIMITER +
               booking.getDestination() + DELIMITER +
               booking.getBookingTime().format(DATE_FORMATTER) + DELIMITER +
               booking.getCarId() + DELIMITER +
               booking.getDriverId() + DELIMITER +
               booking.getDistance() + DELIMITER +
               booking.getStatus();
    }
    
    private Booking stringToBooking(String line) {
        try {
            String[] parts = line.split("\\|\\|\\|");
            if (parts.length == 9) {
                Booking booking = new Booking();
                booking.setBookingNumber(parts[0]);
                booking.setCustomerRegNumber(parts[1]);
                booking.setPickupLocation(parts[2]);
                booking.setDestination(parts[3]);
                booking.setBookingTime(LocalDateTime.parse(parts[4], DATE_FORMATTER));
                booking.setCarId(parts[5]);
                booking.setDriverId(parts[6]);
                booking.setDistance(Double.parseDouble(parts[7]));
                booking.setStatus(parts[8]);
                return booking;
            }
        } catch (Exception e) {
            System.err.println("Error parsing booking data: " + e.getMessage());
        }
        return null;
    }
}