package com.megacitycab.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    
    public static void writeToFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    public static void writeToFile(String filePath, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    public static List<String> readFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            // If file doesn't exist, create an empty file
            if (e instanceof FileNotFoundException) {
                try {
                    File file = new File(filePath);
                    file.createNewFile();
                } catch (IOException ex) {
                    System.err.println("Error creating file: " + ex.getMessage());
                }
            } else {
                System.err.println("Error reading from file: " + e.getMessage());
            }
        }
        
        return lines;
    }
    
    public static void deleteLineFromFile(String filePath, String lineToDelete) {
        List<String> lines = readFromFile(filePath);
        lines.remove(lineToDelete);
        writeToFile(filePath, lines);
    }
    
    public static void updateLineInFile(String filePath, String oldLine, String newLine) {
        List<String> lines = readFromFile(filePath);
        int index = lines.indexOf(oldLine);
        if (index != -1) {
            lines.set(index, newLine);
            writeToFile(filePath, lines);
        }
    }
}