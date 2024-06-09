package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements the {@link OutputStrategy} to write output data into files. Each type of data (label) is written
 * to a separate file.
 */
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory; // Changed naming

    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>(); //Changed naming to camelCase

    /**
     * Constructs a new FileOutputStrategy with a specified base directory.
     *
     * @param baseDirectory the directory where data files will be stored.
     */
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs the data to a file designated for the label provided. If the file does not exist, it will be created.
     *
     * @param patientId the ID of the patient
     * @param timeStamp the timestamp when the data was recorded
     * @param label the label describing the type of data (e.g., ECG, Saturation)
     * @param data the actual data to be output
     */
    @Override
    public void output(int patientId, long timeStamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        //Changed naming
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timeStamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}