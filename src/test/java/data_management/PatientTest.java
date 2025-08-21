package data_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.data_management.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient(101);
    }

    @Test
    void testAddAndRetrieveSingleRecord() {
        patient.addRecord(98.6, "Temperature", 1609459200000L); // Jan 1, 2021
        List<PatientRecord> records = patient.getRecords(1609459190000L, 1609459210000L);
        assertEquals(1, records.size());
        assertEquals("Temperature", records.get(0).getRecordType());
    }

    @Test
    void testGetRecordsWhenEmpty() {
        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        assertTrue(records.isEmpty(), "Should return empty list when no records exist");
    }

    @Test
    void testRecordAtTimeBoundariesIncluded() {
        long ts = 1609459200000L;
        patient.addRecord(75, "HeartRate", ts);
        List<PatientRecord> records = patient.getRecords(ts, ts);
        assertEquals(1, records.size(), "Should include records exactly at the boundary");
    }

    @Test
    void testNegativeTimestamp() {
        patient.addRecord(120, "BloodPressure", -100000000L); // before UNIX epoch
        List<PatientRecord> records = patient.getRecords(-200000000L, 0);
        assertEquals(1, records.size());
        assertEquals(-100000000L, records.get(0).getTimestamp());
    }

    @Test
    void testMultipleRecordsAndFiltering() {
        patient.addRecord(120, "BP", 1000L);
        patient.addRecord(80, "HR", 2000L);
        patient.addRecord(99.1, "Temp", 3000L);

        List<PatientRecord> filtered = patient.getRecords(1500L, 2500L);
        assertEquals(1, filtered.size());
        assertEquals("HR", filtered.get(0).getRecordType());
    }

    @Test
    void testNoRecordsInTimeRange() {
        patient.addRecord(120, "BP", 5000L);
        List<PatientRecord> records = patient.getRecords(6000L, 7000L);
        assertTrue(records.isEmpty());
    }

    @Test
    void testDuplicateTimestamps() {
        long ts = 1000000000L;
        patient.addRecord(60, "HR", ts);
        patient.addRecord(61, "HR", ts);

        List<PatientRecord> records = patient.getRecords(ts, ts);
        assertEquals(2, records.size(), "Should return both records with the same timestamp");
    }

    @Test
    void testGetPatientId() {
        assertEquals(101, patient.getPatientId());
    }
}
