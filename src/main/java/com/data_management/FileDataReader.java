package com.data_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileDataReader implements DataReader {

    private final String outputDir;

    public FileDataReader(String outputDir) {
        this.outputDir = outputDir;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("Output directory not found: " + outputDir);
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            throw new IOException("No data files found in directory: " + outputDir);
        }

        for (File file : files) {
            readFile(file, dataStorage);
        }
    }

    private void readFile(File file, DataStorage dataStorage) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                try {

                    String[] parts = line.split(", ");
                    if (parts.length != 4) continue;

                    int patientId = Integer.parseInt(parts[0].split(": ")[1].trim());
                    long timestamp = Long.parseLong(parts[1].split(": ")[1].trim());
                    String label = parts[2].split(": ")[1].trim();
                    double data = Double.parseDouble(parts[3].split(": ")[1].trim());

                    dataStorage.addPatientData(patientId, data, label, timestamp);
                } catch (Exception e) {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        }
    }

    @Override
    public void startReading(){
        
    }

    @Override
    public void stopReading(){
        
    }
}