package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class implements the OutputStrategy interface to output patient data to files.
 * Each type of data is written to a separate file in the specified base directory.
 */
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory; //changed name to lowerCamelCase

    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();

    /**
     * Constructs a FileOutputStrategy with the specified base directory
     * 
     * @param baseDirectory the base directory where the data files will be stored
     */
    public FileOutputStrategy(String baseDirectory) { //removed whiteline after opening brackets
        this.baseDirectory = baseDirectory; 
    }

    /**
     * Outputs the alert information for a patient to a file associated with the label.
     *
     * @param patientId The id of the patient.
     * @param timestamp The time at which the data was generated.
     * @param label A label describing the type of data.
     * @param data The specific data associated to the patient.
     * @throws Exception if either creating or finding the directory or a file fails.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        //changed name to lowerCamelCase
        String filePath = file_map.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}