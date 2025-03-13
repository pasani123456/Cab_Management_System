package com.megacitycab.controller;

import com.megacitycab.data.FileHandler;
import com.megacitycab.model.User;
import java.util.List;

public class UserController {
    
    private static final String USER_FILE = "data/users.txt";
    
    public UserController() {
        // Initialize with default admin if no users exist
        List<String> users = FileHandler.readFromFile(USER_FILE);
        if (users.isEmpty()) {
            createDefaultAdmin();
        }
    }
    
    private void createDefaultAdmin() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setRole("Admin");
        
        String userLine = admin.getUsername() + "," + admin.getPassword() + "," + admin.getRole();
        FileHandler.writeToFile(USER_FILE, userLine);
    }
    
    public boolean authenticate(String username, String password) {
        List<String> users = FileHandler.readFromFile(USER_FILE);
        
        for (String userLine : users) {
            String[] parts = userLine.split(",");
            if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {
                return true;
            }
        }
        
        return false;
    }
    
    public String getUserRole(String username) {
        List<String> users = FileHandler.readFromFile(USER_FILE);
        
        for (String userLine : users) {
            String[] parts = userLine.split(",");
            if (parts.length >= 3 && parts[0].equals(username)) {
                return parts[2];
            }
        }
        
        return "User"; // Default role
    }
    
    public boolean addUser(User user) {
        if (userExists(user.getUsername())) {
            return false;
        }
        
        String userLine = user.getUsername() + "," + user.getPassword() + "," + user.getRole();
        FileHandler.writeToFile(USER_FILE, userLine);
        return true;
    }
    
    public boolean userExists(String username) {
        List<String> users = FileHandler.readFromFile(USER_FILE);
        
        for (String userLine : users) {
            String[] parts = userLine.split(",");
            if (parts.length > 0 && parts[0].equals(username)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean updateUser(String oldUsername, User updatedUser) {
        List<String> users = FileHandler.readFromFile(USER_FILE);
        boolean updated = false;
        
        for (int i = 0; i < users.size(); i++) {
            String userLine = users.get(i);
            String[] parts = userLine.split(",");
            
            if (parts.length > 0 && parts[0].equals(oldUsername)) {
                String newUserLine = updatedUser.getUsername() + "," + 
                                    updatedUser.getPassword() + "," + 
                                    updatedUser.getRole();
                users.set(i, newUserLine);
                updated = true;
                break;
            }
        }
        
        if (updated) {
            FileHandler.writeToFile(USER_FILE, users);
        }
        
        return updated;
    }
    
    public boolean deleteUser(String username) {
        List<String> users = FileHandler.readFromFile(USER_FILE);
        String lineToRemove = null;
        
        for (String userLine : users) {
            String[] parts = userLine.split(",");
            if (parts.length > 0 && parts[0].equals(username)) {
                lineToRemove = userLine;
                break;
            }
        }
        
        if (lineToRemove != null) {
            users.remove(lineToRemove);
            FileHandler.writeToFile(USER_FILE, users);
            return true;
        }
        
        return false;
    }
}