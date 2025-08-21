package com.cardiogenerator.outputs; // Style: removed underscore from package name

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/** Responsible for outputting patient data to text files. */
public class FileOutputStrategy implements OutputStrategy { // Style: changed class name to upper camel case

    /** The base directory where output files will be saved. */
    private String baseDirectory; // Style: changed variable name to lower camel case

    /** Hashmap that stores the filepath associated with each label */
    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();

    /**
     * Constructs a {@code FileOutputStrategy} that saves output in the specified base directory.
     *
     * @param baseDirectory The base directory where output files will be saved.
     */
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs the patient data to a file. 
     * 
     * If a file for the label already exists, the data is added to the file.
     * Otherwise, a new file is created.
     * 
     * @param patientId The ID of the patient whose data is being output.
     * @param timestamp The timestamp of when the measurement was taken.
     * @param label A label categorizing the type of data.
     * @param data The actual data to be written to the file.
     * @throws IOException if base directory cannot be created.
     * @throws Exception if data cannot be written to the file.
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
        String filePath = file_map.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString()); // Style: changed variable name to lower camel case

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
