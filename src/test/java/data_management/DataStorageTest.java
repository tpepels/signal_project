package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.CholesterolReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.io.IOException;
import java.util.List;

class DataStorageTest {

    private DataStorage storage;
    private CholesterolReader mockedReader;

    @BeforeEach
    void setUp() {
        mockedReader = Mockito.mock(CholesterolReader.class);
        storage = new DataStorage(mockedReader);
    }

    @Test
    void testGetRecordsWithinTimeRange() {
        // Assuming addPatientData works correctly as it is unit tested separately
        int patientId = 1;
        storage.addPatientData(patientId, 200.0, "Cholesterol", 1625072400000L); // Timestamp 1
        storage.addPatientData(patientId, 210.0, "Cholesterol", 1625158800000L); // Timestamp 2
        storage.addPatientData(patientId, 220.0, "Cholesterol", 1625245200000L); // Timestamp 3

        // Define a time range that includes only the second and third records
        long startTime = 1625158800000L; // Start at Timestamp 2
        long endTime = 1625331600000L;   // End after Timestamp 3

        // Retrieve records within the time range
        List<PatientRecord> records = storage.getRecords(patientId, startTime, endTime);

        // Assertions
        assertEquals(2, records.size(), "Should retrieve two records");
        assertEquals(210.0, records.get(0).getMeasurementValue(), "First record value should be 210.0");
        assertEquals(220.0, records.get(1).getMeasurementValue(), "Second record value should be 220.0");
    }

    @Test
    void testGetRecordsNoResultsOutsideTimeRange() {
        int patientId = 1;
        storage.addPatientData(patientId, 200.0, "Cholesterol", 1625072400000L); // Timestamp 1

        // Define a time range that does not include the added record
        long startTime = 1625160000000L; // After Timestamp 1
        long endTime = 1625250000000L;   // Before any new record

        // Retrieve records within the time range
        List<PatientRecord> records = storage.getRecords(patientId, startTime, endTime);

        // Assertions
        assertTrue(records.isEmpty(), "Should return an empty list for out-of-range timestamps");
    }

    @Test
    void testGetRecordsNoResultsForNonExistentPatient() {
        // Define a time range that does not include the added record
        long startTime = 1625160000000L; // After Timestamp 1
        long endTime = 1625250000000L;   // Before any new record

        // Retrieve records within the time range
        List<PatientRecord> records = storage.getRecords(1, startTime, endTime);

        // Assertions
        assertTrue(records.isEmpty(), "Should return an empty list for non-existent patient");
    }

    @Test
    void testGetRecordsForMultiplePatients() {
        // Assuming addPatientData works correctly as it is unit tested separately
        int patientId1 = 1;
        int patientId2 = 2;
        storage.addPatientData(patientId1, 200.0, "Cholesterol", 1625072400000L); // Timestamp 1
        storage.addPatientData(patientId2, 210.0, "Cholesterol", 1625158800000L); // Timestamp 2
        storage.addPatientData(patientId1, 220.0, "Cholesterol", 1625245200000L); // Timestamp 3

        // Define a time range that includes only the first and third records
        long startTime = 1625072400000L; // Start at Timestamp 1
        long endTime = 1625331600000L;   // End after Timestamp 3

        // Retrieve records within the time range for both patients
        List<PatientRecord> records1 = storage.getRecords(patientId1, startTime, endTime);
        List<PatientRecord> records2 = storage.getRecords(patientId2, startTime, endTime);

        // Assertions
        assertEquals(2, records1.size(), "Should retrieve two records for patient 1");
        assertEquals(1, records2.size(), "Should retrieve one record for patient 2");
        assertEquals(200.0, records1.get(0).getMeasurementValue(), "First record value for patient 1 should be 200.0");
        assertEquals(220.0, records1.get(1).getMeasurementValue(), "Second record value for patient 1 should be 220.0");
        assertEquals(210.0, records2.get(0).getMeasurementValue(), "First record value for patient 2 should be 210.0");
    }

    @Test
    void testRecordBoundaries() {
        int patientId = 1;
        storage.addPatientData(patientId, 100.0, "Cholesterol", 1625158800000L); // Exactly at startTime
        storage.addPatientData(patientId, 150.0, "Cholesterol", 1625331600000L); // Exactly at endTime

        long startTime = 1625158800000L;
        long endTime = 1625331600000L;

        List<PatientRecord> records = storage.getRecords(patientId, startTime, endTime);

        assertEquals(2, records.size(), "Should include records at the boundary timestamps");
    }

    @Test
    void testConcurrentAccess() throws InterruptedException {
        int patientId = 1;
        Runnable addData = () -> {
            for (int i = 0; i < 100; i++) {
                storage.addPatientData(patientId, i, "Cholesterol", 1625158800000L + i);
            }
        };

        Thread thread1 = new Thread(addData);
        Thread thread2 = new Thread(addData);
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        List<PatientRecord> records = storage.getRecords(patientId, 1625158800000L, 1625158800100L);
        assertEquals(200, records.size(), "Should handle concurrent data additions correctly");
    }

    @Test
    void testDataIntegrity() {
        int patientId = 1;
        storage.addPatientData(patientId, Double.MAX_VALUE, "Cholesterol", Long.MAX_VALUE);
        storage.addPatientData(patientId, -Double.MAX_VALUE, "Cholesterol", Long.MIN_VALUE);

        List<PatientRecord> records = storage.getRecords(patientId, Long.MIN_VALUE, Long.MAX_VALUE);

        assertEquals(2, records.size(), "Should correctly handle extreme data values");
        assertEquals(Double.MAX_VALUE, records.get(0).getMeasurementValue(), "Check max double value");
        assertEquals(-Double.MAX_VALUE, records.get(1).getMeasurementValue(), "Check min double value");
    }

}
