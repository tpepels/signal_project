package com.alerts.Strategy;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public class OxygenSaturationStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(Patient patient) {
        List<PatientRecord> records = patient.getRecords(patient.getStartTime(), patient.getEndTime());
        if (records.isEmpty()) {
            return false;
        }

        // Get the first and last records which should ideally be within a 10 minute window
        PatientRecord recordFirst = records.get(0);
        PatientRecord recordLast = records.get(records.size() - 1);

        // Check for critical oxygen level drops
        if (recordLast.getMeasurementValue() < 92) {
            return true;
        }

        // Check for significant changes in the measurement values over time
        if (Math.abs(recordFirst.getMeasurementValue() - recordLast.getMeasurementValue()) > 5) {
            return true;
        }

        return false;
    }
}
