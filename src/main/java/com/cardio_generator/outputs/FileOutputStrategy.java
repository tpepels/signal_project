package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code FileOutputStrategy} is an implementation of the {@link OutputStrategy} interface that 
 * handles outputting patient data. It writes the data into files within a specified 
 * directory. If the directory does not exist, it will be created. Each output file is named 
 * according to a label, and data is added to the file.
 *
 * @param baseDirectory The base directory where the output files will be stored.
 * @throws IOException If there is an error while creating the directory or writing to the file.
 */

public class FileOutputStrategy implements OutputStrategy {

    // Changed the name to lowerCamelCase 
    private String baseDirectory;

    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();

    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

/**
 * Outputs patient data to a file. The data is added to a file named after the label
 * in the specified base directory. If the directory or file does not exist, they are created.
 *
 * Each output line includes the patient ID, timestamp, label, and data value.
 *
 * @param patientId the unique identifier of the patient.
 * @param timestamp the time the data was generated.
 * @param label the type of data being output.
 * @param data the actual data value to be recorded.
 *
 * @throws RuntimeException if an error occurs while creating the directory or writing to the file
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
        // Changed the name to lowerCamelCase
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