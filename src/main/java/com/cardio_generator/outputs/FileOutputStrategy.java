package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Providing a strategy for outputting health data to files
 */

 // Class names should start with an uppercase letter
public class FileOutputStrategy implements OutputStrategy {

    // Variable names should start with a lowercase letter
    private String baseDirectory;

    // Final variables need to be all capital letters
    public final ConcurrentHashMap<String, String> FILE_MAP = new ConcurrentHashMap<>();

    /**
     * Constructs a FileOutputStrategy with the specified base directory.
     *
     * @param baseDirectory The base directory where output files will be stored.
     */

    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

/**
* Outputs health data for a specific patient to a file.
 *
 * @param patientId The ID of the patient for whom the data is being outputted.
 * @param timestamp The timestamp associated with the data.
 * @param label     The label or type of data being outputted.
 * @param data      The actual data being outputted.
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
        // Variablename correction
        String filePath = FILE_MAP.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}