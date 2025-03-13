package com.megacitycab.controller;

import com.megacitycab.data.FileHandler;
import com.megacitycab.model.Driver;
import java.util.ArrayList;
import java.util.List;

public class DriverController {
    private static final String DRIVERS_FILE = "data/drivers.txt";
    
    public boolean createDriver(Driver driver) {
        try {
            // Check if driver with the same ID already exists
            if (getDriverById(driver.getDriverId()) != null) {
                return false;
            }
            
            // Convert driver to string format for saving
            String driverLine = convertDriverToString(driver);
            
            // Write to file
            FileHandler.writeToFile(DRIVERS_FILE, driverLine);
            
            return true;
        } catch (Exception e) {
            System.err.println("Error creating driver: " + e.getMessage());
            return false;
        }
    }
    
    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile(DRIVERS_FILE);
        
        for (String line : lines) {
            drivers.add(convertStringToDriver(line));
        }
        
        return drivers;
    }
    
    public Driver getDriverById(String driverId) {
        List<String> lines = FileHandler.readFromFile(DRIVERS_FILE);
        
        for (String line : lines) {
            if (line.startsWith(driverId + ",")) {
                return convertStringToDriver(line);
            }
        }
        
        return null;
    }
    
    public List<Driver> getAvailableDrivers() {
        List<Driver> availableDrivers = new ArrayList<>();
        List<Driver> allDrivers = getAllDrivers();
        
        for (Driver driver : allDrivers) {
            if (driver.isAvailable()) {
                availableDrivers.add(driver);
            }
        }
        
        return availableDrivers;
    }
    
    public boolean updateDriver(Driver driver) {
        List<String> lines = FileHandler.readFromFile(DRIVERS_FILE);
        
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith(driver.getDriverId() + ",")) {
                lines.set(i, convertDriverToString(driver));
                FileHandler.writeToFile(DRIVERS_FILE, lines);
                return true;
            }
        }
        
        return false;
    }
    
    public boolean deleteDriver(String driverId) {
        List<String> lines = FileHandler.readFromFile(DRIVERS_FILE);
        
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith(driverId + ",")) {
                lines.remove(i);
                FileHandler.writeToFile(DRIVERS_FILE, lines);
                return true;
            }
        }
        
        return false;
    }
    
    public boolean updateDriverAvailability(String driverId, boolean available) {
        Driver driver = getDriverById(driverId);
        
        if (driver != null) {
            driver.setAvailable(available);
            return updateDriver(driver);
        }
        
        return false;
    }
    
    private String convertDriverToString(Driver driver) {
        return String.join(",", 
                driver.getDriverId(),
                driver.getName(),
                driver.getLicenseNumber(),
                driver.getTelephone(),
                String.valueOf(driver.isAvailable())
        );
    }
    
    private Driver convertStringToDriver(String line) {
        String[] parts = line.split(",");
        
        // Create a new Driver object using the constructor
        return new Driver(
            parts[0],           // driverId
            parts[1],           // name
            parts[2],           // licenseNumber
            parts[3],           // telephone
            Boolean.parseBoolean(parts[4])  // available
        );
    }
}