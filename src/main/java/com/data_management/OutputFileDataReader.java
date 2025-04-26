package com.data_management;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OutputFileDataReader implements DataReader {

    private final Path outputFilePath;

    public OutputFileDataReader(String[] args) { // this method sets the output place of dir + "data_output.csv"
        this.outputFilePath = parseOutputDirectory(args).resolve("data_output.csv");
    } 

    @Override
    public void readData(DataStorage dataStorage) throws IOException {


        try (BufferedReader reader = Files.newBufferedReader(outputFilePath)) {// this method reads the lines of the information in
            String line;
            boolean isHeader = true;
            DataStorage b= new DataStorage();
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] tokens = line.split(",");
                b.addPatientData(Integer.parseInt(tokens[0]), Double.parseDouble(tokens[1]), tokens[2],Long.parseLong(tokens[3]));
            }

        }
    }

    private Path parseOutputDirectory(String[] args) { //this method is made to get a string array and use this as input for the dir
        for (String arg : args) {
            if (arg.startsWith("--output")) {
                String[] parts = arg.split("file:");
                if (parts.length == 2) {
                    return Paths.get(parts[1].trim());
                }
            }
        }
        throw new IllegalArgumentException("Missing or invalid --output file:<output_dir> argument");
    }

}
