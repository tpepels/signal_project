package com.data_management;

import com.alerts.AlertGenerator;
import com.data_management.Abnormalities.patientState;

import java.util.ArrayList;
import java.util.List;

public class AbnormalityHandler implements Abnormalities {
    private AlertGenerator alertGenerator;

    public AbnormalityHandler(AlertGenerator alertGenerator) {
        this.alertGenerator = alertGenerator;
    }

    @Override
    public void update(Patient patient, patientState state) {
        List<PatientRecord> criticalRecords = new ArrayList<>();

        // Check for critical conditions and save the patient records
        long startTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000); // 24 hours ago
        long endTime = System.currentTimeMillis(); // Current time

        for (PatientRecord record : patient.getRecords(startTime, endTime)) {
            if (state.abnormalHeartRate || state.diastolicMin || state.diastolicMax ||
                    state.lowSaturation || state.systolicMin || state.systolicMax) {
                criticalRecords.add(record);
            }
        }

        // Pass the critical records to the AlertGenerator
        if (!criticalRecords.isEmpty()) {
            alertGenerator.generateAlert(patient, String.valueOf(criticalRecords));
        }
    }

    @Override
    public void update(PatientRecord rec, patientState state) {
        System.out.println(state);
    }
}
