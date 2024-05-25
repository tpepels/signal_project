package com.data_management;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DataProcessor implements DataReader {

    @Override
    public DataStorage readData(DataStorage dataStorage, InputStream data) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(data))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] patientData = line.split(",");
                if (patientData.length == 4) {
                    try {
                        Integer patientId = Integer.parseInt(patientData[0]);
                        Double measurementVal = Double.parseDouble(patientData[1]);
                        String recordType = patientData[2];
                        Long timeStamp = Long.parseLong(patientData[3]);
                        dataStorage.addPatientData(patientId, measurementVal, recordType, timeStamp);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("Invalid data format: " + line);
                }
            }
        }
        return dataStorage;
    }
}

