package com.megacitycab.data;

import com.megacitycab.model.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private static final String FILE_PATH = "data/customers.txt";
    
    public static void addCustomer(Customer customer) {
        FileHandler.writeToFile(FILE_PATH, customer.toString());
    }
    
    public static List<Customer> getAllCustomers() {
        List<String> lines = FileHandler.readFromFile(FILE_PATH);
        List<Customer> customers = new ArrayList<>();
        
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 5) {
                Customer customer = new Customer(parts[0], parts[1], parts[2], parts[3], parts[4]);
                customers.add(customer);
            }
        }
        
        return customers;
    }
    
    public static Customer getCustomerByRegNumber(String regNumber) {
        List<Customer> customers = getAllCustomers();
        
        for (Customer customer : customers) {
            if (customer.getRegNumber().equals(regNumber)) {
                return customer;
            }
        }
        
        return null;
    }
    
    public static Customer getCustomerByTelephone(String telephone) {
        List<Customer> customers = getAllCustomers();
        
        for (Customer customer : customers) {
            if (customer.getTelephone().equals(telephone)) {
                return customer;
            }
        }
        
        return null;
    }
    
    public static void updateCustomer(Customer customer) {
        List<Customer> customers = getAllCustomers();
        List<String> lines = new ArrayList<>();
        
        for (Customer c : customers) {
            if (c.getRegNumber().equals(customer.getRegNumber())) {
                lines.add(customer.toString());
            } else {
                lines.add(c.toString());
            }
        }
        
        FileHandler.writeToFile(FILE_PATH, lines);
    }
    
    public static void deleteCustomer(String regNumber) {
        List<Customer> customers = getAllCustomers();
        List<String> lines = new ArrayList<>();
        
        for (Customer c : customers) {
            if (!c.getRegNumber().equals(regNumber)) {
                lines.add(c.toString());
            }
        }
        
        FileHandler.writeToFile(FILE_PATH, lines);
    }
    
    public static String generateCustomerRegNumber() {
        List<Customer> customers = getAllCustomers();
        int count = customers.size() + 1;
        return "CUST" + String.format("%04d", count);
    }
}