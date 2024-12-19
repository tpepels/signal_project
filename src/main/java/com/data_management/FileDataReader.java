package com.data_management;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Implements a {@link DataReader} that reads and parses data from a file located at a specified directory path.
 * The data read from the file is stored in a {@link DataStorage} instance. This reader specifically handles files
 * where each line represents patient data structured in a comma-separated format. Expected data format per line
 * should contain: Patient ID, Measurement Value, Record Type, and Timestamp.
 */
public class FileDataReader implements DataReader {

    private final String directoryPath;//The directory path where the file containing data is located


    /**
     * Constructs a new {@code FileDataReader} with the specified directory path to the data file.
     *
     * @param directoryPath the directory path where the data file is located. Must not be {@code null}.
     */
    public FileDataReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }


    /**
     * Reads data from the file specified by {@code directoryPath} and stores it in the provided {@code DataStorage}.
     * Each line of the file should consist of four comma-separated values: patient ID (integer), measurement value (double),
     * record type (string), and timestamp (long). Lines that do not conform to this format are logged as invalid.
     *
     * @param dataStorage the {@link DataStorage} instance in which the read data will be stored.
     * @throws IOException if an I/O error occurs opening or reading from the file.
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        // TODO Implement this method
        try(BufferedReader br = new BufferedReader(new FileReader(directoryPath))){
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int patineId = Integer.parseInt(parts[0]);
                    double measurementValue = Double.parseDouble(parts[1]);
                    String recordType = parts[2];
                    long timestamp = Long.parseLong(parts[3]);
                    dataStorage.addPatientData(patineId, measurementValue, recordType, timestamp);
                }else{
                    System.out.println("Invalid line: " + line);
                }

            }
        }
    }

}
