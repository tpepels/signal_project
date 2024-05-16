package com.data_management;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

public class Reader implements DataReader {

    private Path directoryPath;

    public Reader(String directoryPath) {
        this.directoryPath = Path.of(directoryPath);
    }
    public Reader(){}

//    /**
//     * Reads data from a specified file and stores it in the provided DataStorage object.
//     *
//     * @param dataStorage the storage where data will be stored
//     * @throws IOException
//     */
//    @Override
//    public void readData(DataStorage dataStorage) throws IOException {
//        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                parseAndStoreData(line, dataStorage);
//            }
//        } catch (IOException e) {
//            System.err.println("Error reading file: " + filePath);
//            throw e;
//        }
//    }
//
//    /**
//     * Using parsed data to add a new patient in dataStorage
//     *
//     * @param line
//     * @param dataStorage
//     */
//    private void parseAndStoreData(String line, DataStorage dataStorage) {
//        Scanner scanner = new Scanner(line);
//        scanner.useDelimiter(", ");
//        int patiendId = -1;
//        double measurementValue = -1.0;
//        String recordType = "";
//        long timeStamp = 00000000;
//
//
//        //Scan patientID
//        String patiendIdScan = scanner.next();
//        String[] patiendIdArr = patiendIdScan.split(": ");
//        patiendId = Integer.parseInt(patiendIdArr[1]);
//        //Scan timestamp
//        String timeStampScan = scanner.next();
//        String[]timeStampArr = timeStampScan.split(": ");
//        timeStamp = Long.parseLong(timeStampArr[1]);
//        //Scan recordType
//        String label = scanner.next();
//        String[]labelArr= label.split(": ");
//        recordType = labelArr[1];
//        //Scan measurementValue
//        String measurementValueScan = scanner.next();
//        String[]measurementValueArr = measurementValueScan.split(": ");
//        String mVTemp = measurementValueArr[1].split("%")[0];
//        if(mVTemp.equals("triggered")){
//            measurementValue = Double.parseDouble(null);
//        }else{
//            measurementValue = Double.parseDouble(measurementValueArr[1].split("%")[0]);
//        }
//
//        dataStorage.addPatientData(patiendId,measurementValue,recordType,timeStamp);
//
//        }
    /**
     * Iterates through each text file in the directory and processes its data.
     *
     * @param dataStorage the storage where data will be stored
     * @throws IOException
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try (Stream<Path> paths = Files.walk(directoryPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt")) // Filter to include only .txt files
                    .forEach(file -> {
                        try {
                            readDataAll(file, dataStorage); // Assuming readDataAll is a method you've defined
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + file);
                        }
                    });
        }
    }


    /**
     * Reads data from a specified file and stores it in the provided DataStorage object.
     *
     * @param filePath     the path of the file to read data from
     * @param dataStorage  the storage where data will be stored
     * @throws IOException
     */

    public void readDataAll(Path filePath, DataStorage dataStorage) throws IOException {
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
     * Parses a line of data and stores it in dataStorage.
     *
     * @param line         the line to parse
     * @param dataStorage
     * the storage to store data into
     */
    public void parseAndStoreData(String line, DataStorage dataStorage) {
        try (Scanner scanner = new Scanner(line)) {
            scanner.useDelimiter(", ");

            int patientId = Integer.parseInt(scanner.next().split(": ")[1]);
            long timeStamp = Long.parseLong(scanner.next().split(": ")[1]);
            String recordType = scanner.next().split(": ")[1];
            double measurementValue;

            String valueStr = scanner.next().split(": ")[1];
            if(recordType.equals("Alert")){
                if(valueStr.equals("triggered")){
                    measurementValue = 1.0;//triggered
                }
                else{
                    measurementValue=0.0;//resolved
                }
            }else{
                measurementValue = Double.parseDouble(valueStr.split("%")[0]);
            }


            dataStorage.addPatientData(patientId, measurementValue, recordType, timeStamp);
        }
    }

}
