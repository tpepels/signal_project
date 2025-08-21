package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileDataReader implements DataReader {

    private String filePath;

    public FileDataReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // PatientId,value,type,timestamp
                String[] parts = line.trim().split(",");
                if (parts.length != 4) continue; // skip malformed lines

                int patientId = Integer.parseInt(parts[0]);
                double value = Double.parseDouble(parts[1]);
                String type = parts[2];
                long timestamp = Long.parseLong(parts[3]);

                // Use the right method to add data to DataStorage
                dataStorage.addPatientData(patientId, value, type, timestamp);
            }
        }
    }
}