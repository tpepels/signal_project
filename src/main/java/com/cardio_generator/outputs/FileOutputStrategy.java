package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory; // Changed variable name "BaseDirectory" to follow camelCase convention

    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>(); 

    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory)); // Changed variable name "BaseDirectory" to follow camelCase convention
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString()); // Changed variable name "BaseDirectory" to follow camelCase convention

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Path.of(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (IOException e) { // Catch specific exception type
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
