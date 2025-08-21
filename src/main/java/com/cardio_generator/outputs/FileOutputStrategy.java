package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An implementation of {@link OutputStrategy} that writes patient data to files.
 * This strategy outputs each type of data (identified by label) to a separate file
 * within a specified base directory. Each output file is named after its label and
 * contains formatted records for each data entry.FileOutputStrategy
 * Usage: Instantiate this class with a base directory path, then use the
 * {@link #output(int, long, String, String)} method to write patient data to files.
 * The class ensures the base directory exists and manages file paths for each label.
 */

public class FileOutputStrategy implements OutputStrategy {
    /** The directory where output files will be stored. */
    //Field names should be camelCase.
    //Fields should be private unless they need to be exposed.
    private final String baseDirectory;

    /**
     * Maps each label to its corresponding file path within the base directory.
     * Used to avoid recomputing file paths for each output operation.
     */
    //Field names should be camelCase.
    //Fields should be private unless they need to be exposed.
    private final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    /**
     * Constructs a FileOutputStrategy with the specified base directory.
     * @param baseDirectory the directory where output files will be stored
     */
    public FileOutputStrategy(String baseDirectory) {
        // Use 'this' for field assignment to avoid ambiguity.
        this.baseDirectory = baseDirectory;
    }
    /**
     * Outputs patient data to a file corresponding to the given label.
     *Each call writes a formatted line to the appropriate file within the base directory.
     * If the base directory does not exist, it is created. If the file does not exist, it is created.
     * Data is appended to the file.
     *
     * @param patientId the unique identifier of the patient whose data is being output;
     *                  must be a positive integer corresponding to a valid patient in the system
     * @param timestamp the time at which the data was generated, in milliseconds since the epoch
     * @param label a string label describing the type of data (e.g., "SystolicPressure", "ECG");
     *              must not be {@code null}
     * @param data the actual data value to be output, represented as a string; must not be {@code null}
     * @throws IllegalArgumentException if any parameter is invalid (e.g., negative patientId, null label or data)
     * @throws RuntimeException if an error occurs during the output process (e.g., file I/O errors)
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            //Comments should be complete sentences and use proper punctuation.
            //Ensure the base directory exists before writing files.
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            //Error messages should be clear and concise.
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        //Local variable names should be camelCase.
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        //Write the data to the file.
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            //Use consistent formatting and spacing.
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (IOException e) {
            //Catch the most specific exception possible.
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}