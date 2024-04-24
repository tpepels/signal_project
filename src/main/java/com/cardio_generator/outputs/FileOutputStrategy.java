package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a strategy for outputting data to files.
 * This class implements the OutputStrategy interface.
 * It provides methods to output data to files with specified labels.
 * Each label corresponds to a separate file in the specified base directory.
 */
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory; // Corrected variable name to start with a lowercase letter and use camel case

    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>(); // Corrected variable name to use camel case

    /**
     * Constructs a FileOutputStrategy with the specified base directory.
     *
     * @param baseDirectory The base directory where output files will be stored.
     */
    public FileOutputStrategy(String baseDirectory) { // Corrected constructor name to match class name
        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs the data to a file with the specified label.
     *
     * @param patientId  The ID of the patient.
     * @param timestamp  The timestamp of the data.
     * @param label      The label associated with the data.
     * @param data       The data to be outputted.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory)); // Used lower camel case for variable name
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the filePath variable
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString()); // Used lower camel case for variable name

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
