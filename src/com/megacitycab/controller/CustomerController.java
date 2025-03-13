package com.megacitycab.controller;

import com.megacitycab.data.FileHandler;
import com.megacitycab.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerController {
    private static final String CUSTOMER_FILE = "data/customers.txt";
    private static final String DELIMITER = "|||";

    // Add a new customer
    public boolean addCustomer(Customer customer) {
        try {
            // Generate customer registration number if not provided
            if (customer.getRegNumber() == null || customer.getRegNumber().isEmpty()) {
                customer.setRegNumber("CUST" + UUID.randomUUID().toString().substring(0, 8));
            }

            // Check if customer with the same registration number already exists
            List<Customer> customers = getAllCustomers();
            for (Customer c : customers) {
                if (c.getRegNumber().equals(customer.getRegNumber())) {
                    return false; // Customer with the same registration number already exists
                }
            }

            // Convert customer to string and save to file
            String customerData = customerToString(customer);
            FileHandler.writeToFile(CUSTOMER_FILE, customerData);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding customer: " + e.getMessage());
            return false;
        }
    }

    // Update an existing customer
    public boolean updateCustomer(String regNumber, Customer updatedCustomer) {
        List<Customer> customers = getAllCustomers();
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        for (Customer customer : customers) {
            if (customer.getRegNumber().equals(regNumber)) {
                updatedLines.add(customerToString(updatedCustomer));
                found = true;
            } else {
                updatedLines.add(customerToString(customer));
            }
        }

        if (found) {
            FileHandler.writeToFile(CUSTOMER_FILE, updatedLines);
            return true;
        }
        return false;
    }

    // Delete a customer by registration number
    public boolean deleteCustomer(String regNumber) {
        List<Customer> customers = getAllCustomers();
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        for (Customer customer : customers) {
            if (!customer.getRegNumber().equals(regNumber)) {
                updatedLines.add(customerToString(customer));
            } else {
                found = true;
            }
        }

        if (found) {
            FileHandler.writeToFile(CUSTOMER_FILE, updatedLines);
            return true;
        }
        return false;
    }

    // Search for customers by a search term (matches name, NIC, or telephone)
    public List<Customer> searchCustomers(String searchTerm) {
        List<Customer> customers = getAllCustomers();
        List<Customer> matchingCustomers = new ArrayList<>();

        for (Customer customer : customers) {
            if (customer.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                customer.getNic().toLowerCase().contains(searchTerm.toLowerCase()) ||
                customer.getTelephone().contains(searchTerm)) {
                matchingCustomers.add(customer);
            }
        }

        return matchingCustomers;
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile(CUSTOMER_FILE);

        for (String line : lines) {
            Customer customer = stringToCustomer(line);
            if (customer != null) {
                customers.add(customer);
            }
        }

        return customers;
    }

    // Get a customer by registration number
    public Customer getCustomerByRegNumber(String regNumber) {
        List<Customer> customers = getAllCustomers();
        for (Customer customer : customers) {
            if (customer.getRegNumber().equals(regNumber)) {
                return customer;
            }
        }
        return null;
    }

    // Convert a Customer object to a string for file storage
    private String customerToString(Customer customer) {
        return customer.getRegNumber() + DELIMITER +
               customer.getName() + DELIMITER +
               customer.getAddress() + DELIMITER +
               customer.getNic() + DELIMITER +
               customer.getTelephone();
    }

    // Convert a string from the file to a Customer object
    private Customer stringToCustomer(String line) {
        try {
            String[] parts = line.split("\\|\\|\\|");
            if (parts.length == 5) {
                Customer customer = new Customer();
                customer.setRegNumber(parts[0]);
                customer.setName(parts[1]);
                customer.setAddress(parts[2]);
                customer.setNic(parts[3]);
                customer.setTelephone(parts[4]);
                return customer;
            }
        } catch (Exception e) {
            System.err.println("Error parsing customer data: " + e.getMessage());
        }
        return null;
    }
}