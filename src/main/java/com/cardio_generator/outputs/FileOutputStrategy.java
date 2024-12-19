package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Some of the documentation I consulted on chatgpt;

 * Implements an output strategy for writing data to files based on different labels.
 * This class manages file paths and directories dynamically, creating or appending to
 * specific label-based text files within a designated base directory.
 * Usage: Instantiate with a base directory and then use the output method to write data
 * for specific patients identified by a unique ID.
 * @author Tepels
 */

// Corrected class name to UpperCamelCase
public class FileOutputStrategy implements OutputStrategy {

    // Change variable name to lowerCamelCase and private visibility
    private String baseDirectory;

    // Changed to private visibility and corrected naming to UPPER_CASE for constants
    private final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();
    /**
     * Constructs a new FileOutputStrategy with a specified base directory for storing output files.
     *
     * @param baseDirectory the root directory where output files will be managed and stored
     */

    // Constructor name change to match class name and parameter to lowerCamelCase
    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }
    /**
     * Writes formatted patient data to a file determined by the label. If the file does not exist,
     * it is created. If it does exist, data is appended to the end of the file.
     *
     * @param patientId the unique identifier of the patient
     * @param timestamp the timestamp of the data recording
     * @param label the category or type of data being recorded (e.g., "Alert", "VitalStats")
     * @param data the actual data to be written into the file
     * @throws IOException if an error occurs during file creation or data writing
     */

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory, variable name changed to lowerCamelCase
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }

        // change variable name to lowerCamelCase
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            // More specific exception handling could be implemented here if desired
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }

    }
}
