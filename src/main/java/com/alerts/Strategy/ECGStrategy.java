package com.alerts.Strategy;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public class ECGStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(Patient patient) {
        List<PatientRecord> lastTenMinutesECG = patient.lastTenMinutesOfType("ECG"); // Assuming this method exists
        if (lastTenMinutesECG.isEmpty()) {
            return false;
        }

        // Get the most recent ECG record
        PatientRecord lastECGRecord = lastTenMinutesECG.get(0);

        // Check if the latest ECG record is out of normal range
        if (lastECGRecord.getMeasurementValue() < 50 || lastECGRecord.getMeasurementValue() > 100) {
            return true;
        }

        // Check for significant beat-to-beat variation
        for (int i = lastTenMinutesECG.size() - 1; i > 0; i--) {
            if (Math.abs(lastTenMinutesECG.get(i).getMeasurementValue() - lastTenMinutesECG.get(i - 1).getMeasurementValue()) >= 20) {
                return true;
            }
        }

        return false;
    }
}
