package com.cardio_generator.generators;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.alerts.Alert;
import com.cardio_generator.HealthDataSimulator;

import java.util.Arrays;
import java.util.List;

public class AlertGeneratorTest {
    private AlertGenerator alertGenerator;
    private Patient patient;

    @Before
    public void setUp() {
        DataStorage dataStorage = new MockDataStorage();
        alertGenerator = new AlertGenerator(dataStorage);
        patient = new Patient("1");
    }

    @Test
    public void testIncreasingTrendAlert() {
        patient.addRecord(new BloodPressureRecord("1", System.currentTimeMillis() - 3000, 100, 70));
        patient.addRecord(new BloodPressureRecord("1", System.currentTimeMillis() - 2000, 115, 80));
        patient.addRecord(new BloodPressureRecord("1", System.currentTimeMillis() - 1000, 130, 90));
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().contains("Trend Alert: Blood Pressure Trend Detected"));
    }

    @Test
    public void testDecreasingTrendAlert() {
        patient.addRecord(new BloodPressureRecord("1", System.currentTimeMillis() - 3000, 130, 90));
        patient.addRecord(new BloodPressureRecord("1", System.currentTimeMillis() - 2000, 115, 80));
        patient.addRecord(new BloodPressureRecord("1", System.currentTimeMillis() - 1000, 100, 70));
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().contains("Trend Alert: Blood Pressure Trend Detected"));
    }

    @Test
    public void testCriticalThresholdAlerts() {
        patient.addRecord(new BloodPressureRecord("1", System.currentTimeMillis(), 185, 125));
        patient.addRecord(new BloodPressureRecord("1", System.currentTimeMillis(), 85, 55));
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().contains("Critical Threshold Alert: Abnormal Blood Pressure Detected"));
    }

    
    @Test
    public void testLowSaturationAlert() {
        patient.addRecord(new SaturationRecord("1", System.currentTimeMillis(), 90));
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().contains("Low Saturation Alert"));
    }

    @Test
    public void testRapidDropAlert() {
        patient.addRecord(new SaturationRecord("1", System.currentTimeMillis() - 600000, 100));  // 10 minutes ago
        patient.addRecord(new SaturationRecord("1", System.currentTimeMillis(), 93));
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().contains("Rapid Drop in Saturation Alert"));
    }

    @Test
    public void testHypotensiveHypoxemiaAlert() {
        Patient patient = new Patient("1");
        patient.addRecord(new BloodPressureRecord("1", System.currentTimeMillis(), 85, 80));
        patient.addRecord(new SaturationRecord("1", System.currentTimeMillis(), 91));
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().contains("Hypotensive Hypoxemia Alert"));
    }
   
    @Test
    public void testGetBloodPressureRecordsForPatient() {
        DataStorage dataStorage;
        List<BloodPressureRecord> bpRecords = dataStorage.getBloodPressureRecordsForPatient("1");
        assertEquals(2, bpRecords.size());
    }

    @Test
    public void testGetSaturationRecordsForPatient() {
        DataStorage dataStorage;
        List<SaturationRecord> satRecords = dataStorage.getSaturationRecordsForPatient("1");
        assertEquals(1, satRecords.size());
    }

    @Test
    public void testAbnormalHeartRateAlert() {
        patient.addRecord(new ECGRecord("1", System.currentTimeMillis(), 45, Arrays.asList(0.8, 0.8)));
        patient.addRecord(new ECGRecord("1", System.currentTimeMillis(), 105, Arrays.asList(0.8, 0.8)));
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().contains("Abnormal Heart Rate Alert"));
    }

    @Test
    public void testIrregularBeatAlert() {
        patient.addRecord(new ECGRecord("1", System.currentTimeMillis(), 75, Arrays.asList(0.8, 0.9, 1.1)));
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().contains("Irregular Beat Alert"));
    }

    public class HealthDataSimulatorTest {
    private HealthDataSimulator simulator;
    private DataStorage dataStorage;
    private AlertGenerator alertGenerator;

    @Before
    public void setUp() {
        dataStorage = new DataStorage(); // Assume this is properly implemented
        alertGenerator = new AlertGenerator();
        simulator = new HealthDataSimulator(dataStorage, alertGenerator);
    }

    @Test
    public void testManualAlertTrigger() {
        simulator.triggerManualAlert("1");
        assertTrue(alertGenerator.getAlerts().contains(new Alert("1", "Manual Alert Triggered", System.currentTimeMillis())));
    }
}
}








