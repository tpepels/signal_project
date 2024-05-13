package com.cardio_generator.generators;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

/**
 * A class that implements the DataReader interface to read health data from files.
 * It processes files from a specified directory, parsing each line to create PatientRecord objects.
 */
public class FileDataReader implements DataReader {
    // Reference to DataStorage where parsed records will be stored.
    private DataStorage storage;

    /**
     * Constructor for FileDataReader.
     * @param storage A DataStorage instance where the read records will be stored.
     */
    public FileDataReader(DataStorage storage) {
        this.storage = storage;
    }

    /**
     * Reads and processes data from the specified directory.
     * @param outputDirectory The directory from which files will be read.
     */
    @Override
    public void readData(String outputDirectory) {
        try {
            // Walk through the directory, processing each regular file.
            Files.walk(Paths.get(outputDirectory))
                 .filter(Files::isRegularFile)
                 .forEach(this::processFile);
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }

    /**
     * Processes a single file to read its contents and parse them into patient records.
     * @param file The path to the file being processed.
     */
    private void processFile(Path file) {
        try {
            // Read all lines from the file and process them one by one.
            List<String> lines = Files.readAllLines(file);
            for (String line : lines) {
                PatientRecord record = parseLineToRecord(line);
                if (record != null) {
                    // Add the parsed record to storage if it is valid.
                    storage.addRecord(record);
                }
            }
        } catch (IOException e) {
            System.err.println("Error processing file: " + file + "; " + e.getMessage());
        }
    }

    /**
     * Parses a line of text into a PatientRecord object.
     * @param line A single line of text from a file representing patient data.
     * @return A PatientRecord object or null if the line is incorrectly formatted.
     */
    private PatientRecord parseLineToRecord(String line) {
        // Split the line by commas to extract individual fields.
        String[] parts = line.split(",");
        if (parts.length < 6) {
            System.err.println("Incorrect line format: " + line);
            return null;
        }
        try {
            String patientId = parts[0].trim();
            String timestamp = parts[1].trim();
            int heartRate = Integer.parseInt(parts[2].trim());
            int systolic = Integer.parseInt(parts[3].trim());
            int diastolic = Integer.parseInt(parts[4].trim());
            int oxygenSaturation = Integer.parseInt(parts[5].trim());
            // Create and return a new PatientRecord with parsed data.
            return new PatientRecord(patientId, timestamp, heartRate, systolic, diastolic, oxygenSaturation);
        } catch (NumberFormatException e) {
            System.err.println("Error processing numbers: " + line);
            return null;
        }
    }
}
