package com.data_management;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CholesterolReader implements DataReader{

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        File outputFile = new File(System.getProperty("user.dir") + "/output/Cholesterol.txt");
        Scanner reader = new Scanner(outputFile);

        while (reader.hasNextLine()) {
            String data = reader.nextLine();

            Scanner dataScanner = new Scanner(data);

            dataScanner.skip("Patient ID: ");
            
            String patientIdString = dataScanner.next();
            int patientId = Integer.parseInt(patientIdString.substring(0, patientIdString.length() - 1));

            dataScanner.skip(" Timestamp: ");

            String timeStampString = dataScanner.next();
            long timeStamp = Long.parseLong(timeStampString.substring(0, timeStampString.length() - 1));

            dataScanner.skip(" Label: Cholesterol, Data: ");

            double measurementValue = Double.parseDouble(dataScanner.next());

            dataStorage.addPatientData(patientId, measurementValue, "Cholesterol", timeStamp);
            dataScanner.close();
        }
        reader.close();
    }

}
