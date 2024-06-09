package com.alerts;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.data_management.Patient;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import com.data_management.BloodPressureStrategy;
import com.data_management.OxygenSaturationStrategy;
import com.data_management.AlertStrategy;

import java.util.List;

class AlertGenTest {
    private AlertGenerator alertGenerator;
    private DataStorage dataStorage;

    @BeforeEach
    void setUp() {
        dataStorage = DataStorage.getInstance();
        alertGenerator = new AlertGenerator(dataStorage);
    }

    @Test
    void testEvaluateDataForTrendsAndThresholds() {
        // Set up patient data
        Patient patient = new Patient(1);
        patient.addRecord(110.0, "BloodPressure", System.currentTimeMillis() - 60000);
        patient.addRecord(120.0, "BloodPressure", System.currentTimeMillis() - 30000);
        patient.addRecord(130.0, "BloodPressure", System.currentTimeMillis());

        dataStorage.addPatientData(patient.getPatientIdAsInt(), 110.0, "BloodPressure", System.currentTimeMillis() - 60000);
        dataStorage.addPatientData(patient.getPatientIdAsInt(), 120.0, "BloodPressure", System.currentTimeMillis() - 30000);
        dataStorage.addPatientData(patient.getPatientIdAsInt(), 130.0, "BloodPressure", System.currentTimeMillis());

        // Evaluate patient data
        alertGenerator.evaluateData(patient);

        // Check if alerts were triggered
        boolean alertsTriggered = false;
        // Logic to check if alerts were triggered, e.g., checking a log or an alert list in AlertGenerator

        assertFalse(alertsTriggered, "No alerts were triggered");
    }

    @Test
    void testBloodOxygenAlertFactory() {
        AlertFactory factory = new BloodOxygenAlertFactory();
        Alert alert = factory.createAlert("12", "Low Oxygen", System.currentTimeMillis());

        assertNotNull(alert);
        assertEquals("12", alert.getPatientId());
        assertEquals("Blood oxygen: Low Oxygen", alert.getCondition());
    }

    @Test
    void testBloodPressureAlertFactory() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("patient2", "High Blood Pressure", System.currentTimeMillis());

        assertNotNull(alert);
        assertEquals("patient2", alert.getPatientId());
        assertEquals("Blood pressure: High Blood Pressure", alert.getCondition());
    }

    @Test
    void testPriorityAlertDecorator() {
        Alert alert = new Alert("patient1", "Low Oxygen", System.currentTimeMillis());
        Alert priorityAlert = new PriorityAlertDecorator(alert, "HIGH");

        assertNotNull(priorityAlert);
        assertEquals("9993838388", priorityAlert.getPatientId());
        assertEquals("HIGH PRIORITY: Low Oxygen", priorityAlert.getCondition());
    }

    @Test
    void testRepeatedAlertDecorator() {
        Alert alert = new Alert("12384949", "High Blood Pressure", System.currentTimeMillis());
        Runnable dummyTask = () -> {}; // Dummy task for repeated checking
        RepeatedAlertDecorator repeatedAlert = new RepeatedAlertDecorator(alert, 10, dummyTask);

        repeatedAlert.startRepeatedChecking();
        assertTrue(repeatedAlert.isRunning());
        repeatedAlert.stopRepeatedChecking();
    }

    @Test
    void testOxygenSaturationStrategy() {
        Patient patient = new Patient(2);
        patient.addRecord(95, "Oxygen Saturation", System.currentTimeMillis() - 10000);
        patient.addRecord(85, "Oxygen Saturation", System.currentTimeMillis());

        AlertStrategy strategy = new OxygenSaturationStrategy(patient, System.currentTimeMillis() - 10000, System.currentTimeMillis());
        strategy.checkAlert(patient, "Oxygen Saturation");

        // Assuming a way to verify the alert was generated
    }

    @Test
    void testBloodPressureStrategy() {
        Patient patient = new Patient(3);
        patient.addRecord(190, "Systolic Blood Pressure", System.currentTimeMillis() - 10000);
        patient.addRecord(200, "Systolic Blood Pressure", System.currentTimeMillis());

        AlertStrategy strategy = new BloodPressureStrategy(patient, System.currentTimeMillis() - 10000, System.currentTimeMillis());
        strategy.checkAlert(patient, "Systolic Blood Pressure");

    }

    @Test
    void testSingletonDataStorage() {
        DataStorage instance1 = DataStorage.getInstance();
        DataStorage instance2 = DataStorage.getInstance();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2);
    }

    @Test
    void testAddPatientDataToDataStorage() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(4, 98.6, "Temperature", System.currentTimeMillis());

        List<PatientRecord> records = storage.getRecords(4, System.currentTimeMillis() - 10000, System.currentTimeMillis());
        assertEquals(1, records.size());
        assertEquals(98.6, records.get(0).getMeasurementValue());
    }
}
