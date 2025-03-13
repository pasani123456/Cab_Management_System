package com.megacitycab.controller;

import com.megacitycab.data.FileHandler;
import com.megacitycab.model.Car;
import java.util.ArrayList;
import java.util.List;

public class CarController {
    private static final String CARS_FILE = "data/cars.txt";
    
    public boolean createCar(Car car) {
        try {
            // Check if car with the same ID already exists
            if (getCarById(car.getCarId()) != null) {
                return false;
            }
            
            // Convert car to string format for saving
            String carLine = convertCarToString(car);
            
            // Write to file
            FileHandler.writeToFile(CARS_FILE, carLine);
            
            return true;
        } catch (Exception e) {
            System.err.println("Error creating car: " + e.getMessage());
            return false;
        }
    }
    
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile(CARS_FILE);
        
        for (String line : lines) {
            cars.add(convertStringToCar(line));
        }
        
        return cars;
    }
    
    public Car getCarById(String carId) {
        List<String> lines = FileHandler.readFromFile(CARS_FILE);
        
        for (String line : lines) {
            if (line.startsWith(carId + ",")) {
                return convertStringToCar(line);
            }
        }
        
        return null;
    }
    
    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        List<Car> allCars = getAllCars();
        
        for (Car car : allCars) {
            if (car.isAvailable()) {
                availableCars.add(car);
            }
        }
        
        return availableCars;
    }
    
    public boolean updateCar(Car car) {
        List<String> lines = FileHandler.readFromFile(CARS_FILE);
        
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith(car.getCarId() + ",")) {
                lines.set(i, convertCarToString(car));
                FileHandler.writeToFile(CARS_FILE, lines);
                return true;
            }
        }
        
        return false;
    }
    
    public boolean deleteCar(String carId) {
        List<String> lines = FileHandler.readFromFile(CARS_FILE);
        
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith(carId + ",")) {
                lines.remove(i);
                FileHandler.writeToFile(CARS_FILE, lines);
                return true;
            }
        }
        
        return false;
    }
    
    public boolean updateCarAvailability(String carId, boolean available) {
        Car car = getCarById(carId);
        
        if (car != null) {
            car.setAvailable(available);
            return updateCar(car);
        }
        
        return false;
    }
    
    private String convertCarToString(Car car) {
        return String.join(",", 
                car.getCarId(),
                car.getModel(),
                car.getPlateNumber(),
                car.getType(),
                String.valueOf(car.isAvailable())
        );
    }
    
    private Car convertStringToCar(String line) {
        String[] parts = line.split(",");
        
        Car car = new Car();
        car.setCarId(parts[0]);
        car.setModel(parts[1]);
        car.setPlateNumber(parts[2]);
        car.setType(parts[3]);
        car.setAvailable(Boolean.parseBoolean(parts[4]));
        
        return car;
    }
}