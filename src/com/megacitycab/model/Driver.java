package com.megacitycab.model;

public class Driver {
    private String driverId;
    private String name;
    private String licenseNumber;
    private String telephone;
    private boolean available;
    
    public Driver(String driverId, String name, String licenseNumber, String telephone, boolean available) {
        this.driverId = driverId;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.telephone = telephone;
        this.available = available;
    }

    public Driver() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    // Getters and Setters
    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    @Override
    public String toString() {
        return driverId + "," + name + "," + licenseNumber + "," + telephone + "," + available;
    }
}