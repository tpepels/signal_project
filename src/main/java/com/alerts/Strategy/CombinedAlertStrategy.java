package com.alerts.Strategy;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public class CombinedAlertStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(Patient patient) {
        List<PatientRecord> bloodPressureRecords = patient.getLastThreeBloodPressure("Systolic");
        List<PatientRecord> oxygenRecords = patient.lastTenMinutesOfType("Saturation");

        if (bloodPressureRecords.size() >= 3 && !oxygenRecords.isEmpty()) {
            PatientRecord lastBP = bloodPressureRecords.get(0);
            PatientRecord lastOxygen = oxygenRecords.get(0);
            return lastBP.getMeasurementValue() < 90 && lastOxygen.getMeasurementValue() < 92;
        }
        return false;
    }
}
