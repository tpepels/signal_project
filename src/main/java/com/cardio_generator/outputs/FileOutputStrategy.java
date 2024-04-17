package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class implements the OutputStrategy interface to output patient data to files.
 */
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory; // Updated variable name to lowerCamelCase

    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();

    /**
     * Constructs a FileOutputStrategy with the specified base directory.
     *
     * @param baseDirectory the base directory to store the output files
     */
    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory; // Removed unnecessary whitespace
    }

    /**
     * Outputs the patient data to a file.
     * Overridden method from the OutputStrategy interface.
     *
     * @param patientId the patient ID
     * @param timeStamp the timestamp of the data
     * @param label the label of the data
     * @param data the data to output
     */
    @Override
    public void output(int patientId, long timeStamp, String label, String data) { // Shifted timeStamp variable to lowerCamelCase
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String FilePath = file_map.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(FilePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timeStamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + FilePath + ": " + e.getMessage());
        }
    }
}