package data_management;

import java.io.IOException;

import com.data_management.DataReader;
import com.data_management.DataStorage;


public class MockDataReader implements DataReader {
    @Override
    public void readData(DataStorage dataStorage)throws IOException {
        // Simulate adding mock data
        dataStorage.addPatientData(1, 98.6, "Temperature", 1714376789000L);
        dataStorage.addPatientData(1, 120.0, "HeartRate", 1714376789050L);
    }
}
