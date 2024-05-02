package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Reader implements DataReader {

    private Path filePath;

    public Reader(String filePath) {
        this.filePath = Path.of(filePath);
    }

    /**
     * Reads data from a specified file and stores it in the provided DataStorage object.
     * @param dataStorage the storage where data will be stored
     * @throws IOException
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseAndStoreData(line, dataStorage);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            throw e;
        }
    }

    /**
     * Using parsed data to add a new patient in dataStorage
     * @param line
     * @param dataStorage
     */
    private void parseAndStoreData(String line, DataStorage dataStorage) {
        String[] parts = line.split(",");
        if (parts.length >= 4) {
            try {
                int patientId = Integer.parseInt(parts[0]);
                double measurementValue = Double.parseDouble(parts[1]);
                String recordType = parts[2];
                long timestamp = Long.parseLong(parts[3]);
                dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing line: " + line);
            }
        }
    }
}
