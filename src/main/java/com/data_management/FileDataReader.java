package com.data_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.alerts.AlertGenerator;

public class FileDataReader implements DataReader {
    private String outputDir;

    /**
     * Constructs a new instance of FileDataReader, specifying the directory containing the output files.
     *
     * @param outputDir the directory where the output files are located
     */
    public FileDataReader(String outputDir) {
        this.outputDir = outputDir;
    }

    /**
     * Reads data from the specified directory and stores it in the data storage.
     *
     * @param dataStorage the storage where data will be stored
     * @throws IOException if there is an error reading the data
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("Invalid directory: " + outputDir);
        }

        for (File file : dir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                readFile(file, dataStorage);
            }
        }
    }

    /**
     * Reads a single file and adds its data to the data storage.
     *
     * @param file the file to read
     * @param dataStorage the storage where data will be stored
     * @throws IOException if there is an error reading the file
     */
    private void readFile(File file, DataStorage dataStorage) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the file format is: patientId,measurementValue,recordType,timestamp
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int patientId = Integer.parseInt(parts[0]);
                    double measurementValue = Double.parseDouble(parts[1]);
                    String recordType = parts[2];
                    long timestamp = Long.parseLong(parts[3]);

                    dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            DataStorage storage = new DataStorage();
            DataReader reader = new FileDataReader("path/to/output_dir");

            reader.readData(storage);

            // Example usage of DataStorage after reading data
            List<PatientRecord> records = storage.getRecords(1, 1700000000000L, 1800000000000L);
            for (PatientRecord record : records) {
                System.out.println("Record for Patient ID: " + record.getPatientId() +
                        ", Type: " + record.getRecordType() +
                        ", Data: " + record.getMeasurementValue() +
                        ", Timestamp: " + record.getTimestamp());
            }

            // Initialize the AlertGenerator with the storage
            AlertGenerator alertGenerator = new AlertGenerator(storage);

            // Evaluate all patients' data to check for conditions that may trigger alerts
            for (Patient patient : storage.getAllPatients()) {
                alertGenerator.evaluateData(patient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
