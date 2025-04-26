package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of {@link OutputStrategy} that writes patient data to files.
 * Each label corresponds to a separate file where the data is appended.
 * Useful for logging or later processing of patient monitoring information.
 *
 * The class ensures thread-safe access using {@link ConcurrentHashMap}.
 */
public class FileOutputStrategy implements OutputStrategy {
    /**
     * The base directory where output files will be created.
     */
    // changed variable name 
    private String baseDirectory;

<<<<<<< HEAD
    /**
     * A mapping from data labels to file paths.
     */
    // changed variable name
=======
    private String baseDirectory;

>>>>>>> a3384e7b374c1c493cd3dd27b62bce7aa8160124
    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    /**
     * Constructs a FileOutputStrategy with the specified base directory.
     *
     * @param baseDirectory The directory in which to store output files.
     */
    public FileOutputStrategy(String baseDirectory) {
<<<<<<< HEAD
        // changed variable name 
        this.baseDirectory = baseDirectory;
=======
      this.baseDirectory = baseDirectory;
>>>>>>> a3384e7b374c1c493cd3dd27b62bce7aa8160124
    }

    /**
     * Outputs patient data by appending it to a file corresponding to the label.
     * If the file does not exist, it is created.
     *
     * @param patientId  The unique identifier of the patient.
     * @param timestamp  The timestamp of the data in milliseconds since epoch.
     * @param label      The type or category of the data (e.g., ECG, Alert).
     * @param data       The actual data content to write.
     * @throws IOException if there is an error creating directories or writing to the file.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
<<<<<<< HEAD
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String FilePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());
=======
      try {
        // Create the directory
        Files.createDirectories(Paths.get(baseDirectory));
      } catch (IOException e) {
        System.err.println("Error creating base directory: " + e.getMessage());
        return;
      }
        // Set the FilePath variable
      String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());
>>>>>>> a3384e7b374c1c493cd3dd27b62bce7aa8160124

        // Write the data to the file
      try (PrintWriter out = new PrintWriter(
          Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
      } catch (Exception e) {
        System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
      }
    }
}