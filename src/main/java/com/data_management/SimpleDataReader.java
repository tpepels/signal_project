package com.data_management;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SimpleDataReader implements DataReader{

    @Override
    public DataStorage readData(DataStorage dataStorage, InputStream data) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(data))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                int patientId = Integer.parseInt(parts[0]);
                double measurementValue = Double.parseDouble(parts[1]);
                String recordType = parts[2];
                long timestamp = Long.parseLong(parts[3]);

                dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
            }
        }
        return dataStorage;
    }
}
