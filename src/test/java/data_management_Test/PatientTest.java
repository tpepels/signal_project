package data_management_Test;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {
    private Patient patient;

    @BeforeEach
    public void setUp() {
        patient = new Patient(1);
    }

    @Test
    public void testAddSingleRecord() {
        patient.addRecord(98.6, "HeartRate", 1622470420000L);
        List<PatientRecord> records = patient.getRecords(1622470420000L, 1622470420000L);

        assertEquals(1, records.size());

        PatientRecord record = records.get(0);

        assertEquals(1, record.getPatientId());
        assertEquals(98.6, record.getMeasurementValue(), 0.01);
        assertEquals("HeartRate", record.getRecordType());
        assertEquals(1622470420000L, record.getTimestamp());
    }

    @Test
    public void testAddMultipleRecords() {
        double[] measurementValues = {98.6, 99.0, 98.7};
        patient.addRecord(measurementValues, "Temperature", 1622470520000L);
        List<PatientRecord> records = patient.getRecords(1622470520000L, 1622470520000L);

        assertEquals(1, records.size());

        PatientRecord record = records.get(0);

        assertEquals(1, record.getPatientId());
        assertArrayEquals(measurementValues, record.getMeasurementValues(), 0.01);
        assertEquals("Temperature", record.getRecordType());
        assertEquals(1622470520000L, record.getTimestamp());
    }

    @Test
    public void testGetRecordsWithinTimeRange() {
        patient.addRecord(98.6, "HeartRate", 1622470420000L);
        patient.addRecord(120.0, "BloodPressure", 1622470520000L);

        List<PatientRecord> records = patient.getRecords(1622470400000L, 1622470500000L);

        assertEquals(1, records.size());

        PatientRecord record = records.get(0);

        assertEquals(1, record.getPatientId());
        assertEquals(98.6, record.getMeasurementValue(), 0.01);
        assertEquals("HeartRate", record.getRecordType());
        assertEquals(1622470420000L, record.getTimestamp());
    }

    @Test
    public void testGetRecordsNoRecordsInTimeRange() {
        patient.addRecord(98.6, "HeartRate", 1622470420000L);
        patient.addRecord(120.0, "BloodPressure", 1622470520000L);

        List<PatientRecord> records = patient.getRecords(1622470600000L, 1622470700000L);
        assertTrue(records.isEmpty());
    }

    @Test
    public void testGetRecordsAllRecordsInTimeRange() {
        patient.addRecord(98.6, "HeartRate", 1622470420000L);
        patient.addRecord(120.0, "BloodPressure", 1622470520000L);

        List<PatientRecord> records = patient.getRecords(1622470400000L, 1622470600000L);

        assertEquals(2, records.size());

        PatientRecord record1 = records.get(0);
        PatientRecord record2 = records.get(1);

        assertEquals(1, record1.getPatientId());
        assertEquals(98.6, record1.getMeasurementValue(), 0.01);
        assertEquals("HeartRate", record1.getRecordType());
        assertEquals(1622470420000L, record1.getTimestamp());

        assertEquals(1, record2.getPatientId());
        assertEquals(120.0, record2.getMeasurementValue(), 0.01);
        assertEquals("BloodPressure", record2.getRecordType());
        assertEquals(1622470520000L, record2.getTimestamp());
    }

    @Test
    public void testGetPatientId() {
        assertEquals(1, patient.getPatientId());
    }

    @Test
    public void testNoRecordsInitially() {
        List<PatientRecord> records = patient.getRecords(1622470400000L, 1622470600000L);
        assertTrue(records.isEmpty());
    }
}

