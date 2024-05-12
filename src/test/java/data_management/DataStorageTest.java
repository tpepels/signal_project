package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.CholesterolReader;
import com.data_management.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.io.IOException;
import java.util.List;

public class DataStorageTest {

    private DataStorage storage;
    private CholesterolReader mockedReader;

    @BeforeEach
    public void setUp() {
        mockedReader = Mockito.mock(CholesterolReader.class);
        storage = new DataStorage(mockedReader);
    }

    @Test
    public void testGetRecordsWithinTimeRange() {
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
    public void testGetRecordsNoResultsOutsideTimeRange() {
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
    public void testGetRecordsNoResultsForNonExistentPatient() {
        // Define a time range that does not include the added record
        long startTime = 1625160000000L; // After Timestamp 1
        long endTime = 1625250000000L;   // Before any new record

        // Retrieve records within the time range
        List<PatientRecord> records = storage.getRecords(1, startTime, endTime);

        // Assertions
        assertTrue(records.isEmpty(), "Should return an empty list for non-existent patient");
    }

    @Test
    public void testGetRecordsForMultiplePatients() {
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
    public void testRecordBoundaries() {
        int patientId = 1;
        storage.addPatientData(patientId, 100.0, "Cholesterol", 1625158800000L); // Exactly at startTime
        storage.addPatientData(patientId, 150.0, "Cholesterol", 1625331600000L); // Exactly at endTime

        long startTime = 1625158800000L;
        long endTime = 1625331600000L;

        List<PatientRecord> records = storage.getRecords(patientId, startTime, endTime);

        assertEquals(2, records.size(), "Should include records at the boundary timestamps");
    }

    @Test
    public void testDataIntegrity() {
        int patientId = 1;
        storage.addPatientData(patientId, Double.MAX_VALUE, "Cholesterol", Long.MAX_VALUE);
        storage.addPatientData(patientId, -Double.MAX_VALUE, "Cholesterol", Long.MIN_VALUE);

        List<PatientRecord> records = storage.getRecords(patientId, Long.MIN_VALUE, Long.MAX_VALUE);

        assertEquals(2, records.size(), "Should correctly handle extreme data values");
        assertEquals(Double.MAX_VALUE, records.get(0).getMeasurementValue(), "Check max double value");
        assertEquals(-Double.MAX_VALUE, records.get(1).getMeasurementValue(), "Check min double value");
    }

    @Test
    public void testReadData() throws IOException {
        storage.loadData();
        Mockito.verify(mockedReader).readData(storage);
    }

    @Test
    public void testGetRecordsByTypeWithRecords() {
        Patient patient = new Patient(1);
        // Add multiple records of different types
        patient.addRecord(120.0, "HeartRate", 1625158800000L);
        patient.addRecord(80.0, "BloodPressure", 1625158800001L);
        patient.addRecord(130.0, "HeartRate", 1625158800002L);

        // Retrieve records of type "HeartRate"
        List<PatientRecord> heartRateRecords = patient.getRecordsByType("HeartRate");

        // Assertions
        assertEquals(2, heartRateRecords.size(), "Should retrieve two HeartRate records");
        assertEquals(120.0, heartRateRecords.get(0).getMeasurementValue(), "Check first HeartRate value");
        assertEquals(130.0, heartRateRecords.get(1).getMeasurementValue(), "Check second HeartRate value");
    }

    @Test
    public void testGetRecordsByTypeWithNoRecordsOfThatType() {
        Patient patient = new Patient(1);
        // Add records of a different type
        patient.addRecord(120.0, "HeartRate", 1625158800000L);
        patient.addRecord(130.0, "HeartRate", 1625158800002L);

        // Try to retrieve records of type "BloodPressure"
        List<PatientRecord> bloodPressureRecords = patient.getRecordsByType("BloodPressure");

        // Assertions
        assertTrue(bloodPressureRecords.isEmpty(), "Should return an empty list for non-existent type");
    }

    @Test
    public void testGetRecordsByTypeCaseSensitivity() {
        Patient patient = new Patient(1);
        // Add records of similar type but different cases
        patient.addRecord(120.0, "HeartRate", 1625158800000L);
        patient.addRecord(85.0, "heartrate", 1625158800001L);
        patient.addRecord(90.0, "HEARTRATE", 1625158800002L);

        // Retrieve records of type "HeartRate" exactly as typed
        List<PatientRecord> heartRateRecords = patient.getRecordsByType("HeartRate");

        // Assertions
        assertEquals(1, heartRateRecords.size(), "Should retrieve one HeartRate record with exact case");
        assertEquals(120.0, heartRateRecords.get(0).getMeasurementValue(), "Check the HeartRate value");

        // Retrieve records of type "heartrate" (lowercase)
        List<PatientRecord> lowerCaseRecords = patient.getRecordsByType("heartrate");

        // Assertions
        assertEquals(1, lowerCaseRecords.size(), "Should retrieve one heartrate record with lowercase");
        assertEquals(85.0, lowerCaseRecords.get(0).getMeasurementValue(), "Check the lowercase HeartRate value");

        // Retrieve records of type "HEARTRATE" (uppercase)
        List<PatientRecord> upperCaseRecords = patient.getRecordsByType("HEARTRATE");

        // Assertions
        assertEquals(1, upperCaseRecords.size(), "Should retrieve one HEARTRATE record with uppercase");
        assertEquals(90.0, upperCaseRecords.get(0).getMeasurementValue(), "Check the uppercase HeartRate value");
    }
}
