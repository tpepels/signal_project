package data_management_Test;

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
        DataStorage.resetInstance();
        dataStorage = DataStorage.getInstance();
    }

    @Test
    public void testAddPatientDataSingleMeasurement() {
        dataStorage.addPatientData(1, 98.6, "HeartRate", 1622470420000L);
        List<Patient> patients = dataStorage.getAllPatients();

        assertEquals(1, patients.size());

        Patient patient = patients.get(0);
        List<PatientRecord> records = patient.getRecords(1622470420000L, 1622470420000L);

        assertEquals(1, records.size());

        PatientRecord record = records.get(0);

        assertEquals(1, record.getPatientId());
        assertEquals(98.6, record.getMeasurementValue(), 0.01);
        assertEquals("HeartRate", record.getRecordType());
        assertEquals(1622470420000L, record.getTimestamp());
    }

    @Test
    public void testAddPatientDataMultipleMeasurements() {
        double[] measurementValues = {98.6, 99.0, 98.7};
        dataStorage.addPatientData(1, measurementValues, "Temperature", 1622470520000L);
        List<Patient> patients = dataStorage.getAllPatients();

        assertEquals(1, patients.size());

        Patient patient = patients.get(0);
        List<PatientRecord> records = patient.getRecords(1622470520000L, 1622470520000L);

        assertEquals(1, records.size());

        PatientRecord record = records.get(0);

        assertEquals(1, record.getPatientId());
        assertArrayEquals(measurementValues, record.getMeasurementValues(), 0.01);
        assertEquals("Temperature", record.getRecordType());
        assertEquals(1622470520000L, record.getTimestamp());
    }

    @Test
    public void testGetRecords() {
        dataStorage.addPatientData(1, 98.6, "HeartRate", 1622470420000L);
        dataStorage.addPatientData(1, 120.0, "BloodPressure", 1622470520000L);

        List<PatientRecord> records = dataStorage.getRecords(1, 1622470400000L, 1622470500000L);

        assertEquals(1, records.size());
        PatientRecord record = records.get(0);

        assertEquals(1, record.getPatientId());
        assertEquals(98.6, record.getMeasurementValue(), 0.01);
        assertEquals("HeartRate", record.getRecordType());
        assertEquals(1622470420000L, record.getTimestamp());
    }

    @Test
    public void testGetAllPatients() {
        dataStorage.addPatientData(1, 98.6, "HeartRate", 1622470420000L);
        dataStorage.addPatientData(2, 120.0, "BloodPressure", 1622470520000L);

        List<Patient> patients = dataStorage.getAllPatients();

        assertEquals(2, patients.size());

        Patient patient1 = patients.get(0);
        Patient patient2 = patients.get(1);

        assertEquals(1, patient1.getRecords(1622470420000L, 1622470420000L).size());
        assertEquals(1, patient2.getRecords(1622470520000L, 1622470520000L).size());
    }

    @Test
    public void testAddPatientDataExistingPatient() {
        dataStorage.addPatientData(1, 98.6, "HeartRate", 1622470420000L);
        dataStorage.addPatientData(1, 120.0, "BloodPressure", 1622470520000L);

        List<Patient> patients = dataStorage.getAllPatients();

        assertEquals(1, patients.size());

        Patient patient = patients.get(0);
        List<PatientRecord> records = patient.getRecords(1622470420000L, 1622470520000L);

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
    public void testNoRecordsForNonExistentPatient() {
        List<PatientRecord> records = dataStorage.getRecords(99, 1622470400000L, 1622470500000L);
        assertTrue(records.isEmpty());
    }

    @Test
    public void testGetRecordsWithNoRecordsInTimeRange() {
        dataStorage.addPatientData(1, 98.6, "HeartRate", 1622470420000L);
        dataStorage.addPatientData(1, 120.0, "BloodPressure", 1622470520000L);

        List<PatientRecord> records = dataStorage.getRecords(1, 1622470600000L, 1622470700000L);
        assertTrue(records.isEmpty());
    }
}
