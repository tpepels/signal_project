package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * the FileOutputStrategy class implements the OutputStrategy interface to provide an output strategy that writes data to files
 */
public class FileOutputStrategy implements OutputStrategy {//Class names are written in UpperCamelCase.

    /** ehe base directory where files will be stored. */
    private String baseDirectory; // Non-constant field names are written in lowerCamelCase

    /**
     * A map of file paths, with labels as keys and file paths as values.
     */
    public final ConcurrentHashMap<String, String> FILE_MAP = new ConcurrentHashMap<>();//Constants are written in UPPER_SNAKE_CASE.

    /**
     * Constructs a new FileOutputStrategy with the specified base directory.
     * @param baseDirectory The base directory where files will be stored.
     */
    public FileOutputStrategy(String baseDirectory) { // Constructor renaming

        this.baseDirectory = baseDirectory;
    }

    /**
     * outputs the generated data for a specific patient
     *
     * @param patientId the id of the patient
     * @param timestamp the timestamp contains the time at which it was generated
     * @param label the label associated with the data
     * @param data the data to be output
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
            /**
             *   @throws IOExceptionn if the baseDirectory can not be accessed
             */
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String filePath = FILE_MAP.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString()); // Local variables are lowerCamelCase

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);

            /**
             *   @throws Exceptionn if it cannot be printed
             */

        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
