package com.alerts;


import com.data_management.Patient;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import com.data_management.TestDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class AlertGenTest {
    private DataStorage dataStorage;
    private AlertGenerator alertGenerator;
    private List<Alert> triggeredAlerts;

    @BeforeEach
    public void setUp() {
        dataStorage = new DataStorage();
        triggeredAlerts = new ArrayList<>();

        alertGenerator = new AlertGenerator(dataStorage) {
            @Override
            protected void triggerAlert(Alert alert) {
                triggeredAlerts.add(alert);
            }
        };
    }

    @Test
    public void testEvaluateDataForTrendsAndThresholds() {
        Patient patient = new Patient(1);
        int pId = patient.getPatientIdAsInt();
        List<PatientRecord> records = TestDataGenerator.generateBloodPressureData(pId, 10);

        for (PatientRecord record : records) {
            patient.addRecord(record.getMeasurementValue(), record.getRecordType(), record.getTimestamp());
        }

        alertGenerator.evaluateData(patient);

        assertFalse(triggeredAlerts.isEmpty(), "No alerts were triggered");

        boolean increasingTrendAlert = triggeredAlerts.stream()
                .anyMatch(alert -> alert.getCondition().equals("Increasing Blood Pressure Trend"));
        assertTrue(increasingTrendAlert, "Increasing Blood Pressure Trend alert was not triggered");

        boolean criticalThresholdAlert = triggeredAlerts.stream()
                .anyMatch(alert -> alert.getCondition().equals("Critical Blood Pressure Threshold"));
        assertTrue(criticalThresholdAlert, "Critical Blood Pressure Threshold alert was not triggered");
    }
}
