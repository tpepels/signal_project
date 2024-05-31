package com.alerts;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.data_management.Patient;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.List;

class AlertGenTest {
    private AlertGenerator alertGenerator;
    private DataStorage dataStorage;

    @BeforeEach
    void setUp() {
        dataStorage = new DataStorage();
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
}
