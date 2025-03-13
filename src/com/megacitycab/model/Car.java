package com.megacitycab.model;

public class Car {
    private String carId;
    private String model;
    private String plateNumber;
    private String type; // "Economy", "Standard", "Premium"
    private boolean available;
    
    public Car(){
        
    }
    
    public Car(String carId, String model, String plateNumber, String type, boolean available) {
        this.carId = carId;
        this.model = model;
        this.plateNumber = plateNumber;
        this.type = type;
        this.available = available;
    }
    
    // Getters and Setters
    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    @Override
    public String toString() {
        return carId + "," + model + "," + plateNumber + "," + type + "," + available;
    }
}