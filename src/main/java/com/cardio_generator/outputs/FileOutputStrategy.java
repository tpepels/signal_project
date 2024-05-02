//https://google.github.io/styleguide/javaguide.html
//https://www.baeldung.com/java-string-to-camel-case
//https://www.baeldung.com/java-map-computeifabsent


package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This file is an implementation of an interface
 * It creates new files corresponding to the labels and moves the information to each type of file
 * Different files correspond to different data labels 
 */
public class FileOutputStrategy implements OutputStrategy {

    // Modify the name to camelCase as read in the sources above
    private String baseDirectory;

    // We continue renaming as before (camelCase)
    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    /**
     * It makes a FileOutputStrategy that is gonna have a specific base directory for file outputs
     *
     * @param baseDirectory the directory path where data files will be created and stored
     */
    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs of the patient healht data, format and organise by data labels and into files
     * Creates a new file for each label if it doesn't exist and adds new data 
     *
     * @param patientId to be able to know which patient information belongs to who
     * @param timestamp moment in which the data was obtained
     * @param label label for the data 
     * @param data data to be output, is a string.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // We realized we needed to create something, and it's a directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Rename to follow camelCase
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Then we write the specific data into the files
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
