package com.megacitycab.data;

import com.megacitycab.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String FILE_PATH = "data/users.txt";
    
    public static void addUser(User user) {
        FileHandler.writeToFile(FILE_PATH, user.toString());
    }
    
    public static List<User> getAllUsers() {
        List<String> lines = FileHandler.readFromFile(FILE_PATH);
        List<User> users = new ArrayList<>();
        
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                User user = new User(parts[0], parts[1], parts[2]);
                users.add(user);
            }
        }
        
        return users;
    }
    
    public static User getUserByUsername(String username) {
        List<User> users = getAllUsers();
        
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        
        return null;
    }
    
    public static boolean validateUser(String username, String password) {
        User user = getUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
    
    public static void updateUser(User user) {
        List<User> users = getAllUsers();
        List<String> lines = new ArrayList<>();
        
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                lines.add(user.toString());
            } else {
                lines.add(u.toString());
            }
        }
        
        FileHandler.writeToFile(FILE_PATH, lines);
    }
    
    public static void deleteUser(String username) {
        List<User> users = getAllUsers();
        List<String> lines = new ArrayList<>();
        
        for (User u : users) {
            if (!u.getUsername().equals(username)) {
                lines.add(u.toString());
            }
        }
        
        FileHandler.writeToFile(FILE_PATH, lines);
    }
    
    // Initialize with default admin user if no users exist
    public static void initializeUsers() {
        List<User> users = getAllUsers();
        if (users.isEmpty()) {
            User admin = new User("admin", "admin123", "admin");
            addUser(admin);
        }
    }
}