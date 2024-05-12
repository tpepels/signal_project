package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.CholesterolReader;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.List;

class DataStorageTest {

    @Test
    void testAddAndGetRecords() {
        CholesterolReader reader = Mockito.mock(CholesterolReader.class);
        // DataReader reader
        DataStorage storage = new DataStorage(reader);
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
    }

    @Test
    void testAddAndGetRecordsWithNoRecords() {
        CholesterolReader reader = Mockito.mock(CholesterolReader.class);
        // DataReader reader
        DataStorage storage = new DataStorage(reader);
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789052L, 1714376789053L);
        assertEquals(0, records.size()); // Check if no records are retrieved
    }

    @Test
    void testAddAndGetRecordsWithNoMatchingRecords() {
        CholesterolReader reader = Mockito.mock(CholesterolReader.class);
        // DataReader reader
        DataStorage storage = new DataStorage(reader);
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789052L, 1714376789053L);
        assertEquals(0, records.size()); // Check if no records are retrieved
    }

    @Test
    void testAddAndGetRecordsWithMultiplePatients() {
        CholesterolReader reader = Mockito.mock(CholesterolReader.class);
        // DataReader reader
        DataStorage storage = new DataStorage(reader);
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);
        storage.addPatientData(2, 300.0, "WhiteBloodCells", 1714376789052L);
        storage.addPatientData(2, 400.0, "WhiteBloodCells", 1714376789053L);

        List<PatientRecord> records = storage.getRecords(2, 1714376789052L, 1714376789053L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(300.0, records.get(0).getMeasurementValue()); // Validate first record
    }

    @Test
    void testAddAndGetRecordsWithMultiplePatientsNoMatchingRecords() {
        CholesterolReader reader = Mockito.mock(CholesterolReader.class);
        // DataReader reader
        DataStorage storage = new DataStorage(reader);
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);
        storage.addPatientData(2, 300.0, "WhiteBloodCells", 1714376789052L);
        storage.addPatientData(2, 400.0, "WhiteBloodCells", 1714376789053L);

        List<PatientRecord> records = storage.getRecords(2, 1714376789050L, 1714376789051L);
        assertEquals(0, records.size()); // Check if no records are retrieved
    }

    @Test
    void testAddAndGetRecordsWithMultiplePatientsNoRecords() {
        CholesterolReader reader = Mockito.mock(CholesterolReader.class);
        // DataReader reader
        DataStorage storage = new DataStorage(reader);
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);
        storage.addPatientData(2, 300.0, "WhiteBloodCells", 1714376789052L);
        storage.addPatientData(2, 400.0, "WhiteBloodCells", 1714376789053L);

        List<PatientRecord> records = storage.getRecords(3, 1714376789050L, 1714376789051L);
        assertEquals(0, records.size()); // Check if no records are retrieved
    }
}
