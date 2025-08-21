package data_management;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataStorageTest {

    private DataStorage dataStorage;

    @BeforeEach
    public void setUp() {
        dataStorage = DataStorage.getInstance();
        // Clear previous patient data if needed
        dataStorage.getAllPatients().clear();
    }

    @Test
    public void testAddAndRetrievePatientData() {
        int patientId = 101;
        long timestamp = System.currentTimeMillis();

        dataStorage.addPatientData(patientId, 98.6, "Temperature", timestamp);
        List<PatientRecord> records = dataStorage.getRecords(patientId, timestamp - 1000, timestamp + 1000);

        assertEquals(1, records.size(), "Expected one record to be added and retrieved.");
        PatientRecord record = records.get(0);
        assertEquals(patientId, record.getPatientId());
        assertEquals("Temperature", record.getRecordType());
        assertEquals(98.6, record.getMeasurementValue(), 0.001);
        assertEquals(timestamp, record.getTimestamp());
    }

    @Test
    public void testGetRecords_NoPatientFound_ReturnsEmptyList() {
        List<PatientRecord> records = dataStorage.getRecords(999, 0, System.currentTimeMillis());
        assertTrue(records.isEmpty(), "Expected empty list for unknown patient ID.");
    }

    @Test
    public void testGetAllPatients() {
        int id1 = 201;
        int id2 = 202;
        long now = System.currentTimeMillis();

        dataStorage.addPatientData(id1, 70, "HeartRate", now);
        dataStorage.addPatientData(id2, 120, "BloodPressure", now);

        List<Patient> allPatients = dataStorage.getAllPatients();

        assertTrue(allPatients.stream().anyMatch(p -> p.getPatientId() == id1));
        assertTrue(allPatients.stream().anyMatch(p -> p.getPatientId() == id2));
    }

    @Test
    public void testMultipleRecordsTimeRangeFiltering() {
        int patientId = 303;
        long now = System.currentTimeMillis();

        dataStorage.addPatientData(patientId, 100, "HeartRate", now - 5000);
        dataStorage.addPatientData(patientId, 110, "HeartRate", now);
        dataStorage.addPatientData(patientId, 120, "HeartRate", now + 5000);

        List<PatientRecord> records = dataStorage.getRecords(patientId, now - 1000, now + 1000);
        assertEquals(1, records.size());
        assertEquals(110, records.get(0).getMeasurementValue(), 0.001);
    }

    @Test
    public void testValidPatientNoMatchingTimeRange() {
        int patientId = 404;
        long now = System.currentTimeMillis();

        dataStorage.addPatientData(patientId, 97.5, "Temperature", now);

        List<PatientRecord> result = dataStorage.getRecords(patientId, now + 1000, now + 2000);
        assertTrue(result.isEmpty(), "No records should be found outside the timestamp range.");
    }

    @Test
    public void testSingletonInstanceConsistency() {
        DataStorage instance1 = DataStorage.getInstance();
        DataStorage instance2 = DataStorage.getInstance();
        assertSame(instance1, instance2, "Expected the same singleton instance.");
    }
}
