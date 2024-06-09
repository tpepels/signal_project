package com.data_management;

import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.alerts.BloodPressureAlertFactory;

import java.util.List;

public class BloodPressureStrategy implements AlertStrategy {
    private final Patient patient;
    private final Long startTime;
    private final Long endTime;

    public BloodPressureStrategy(Patient patient, Long startTime, Long endTime) {
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public void checkAlert(Patient patient, String recordType) {
        if (!recordType.equalsIgnoreCase("Systolic blood pressure") && !recordType.equalsIgnoreCase("Diastolic blood pressure")) {
            throw new IllegalArgumentException("Please provide a valid record type");
        }

        boolean systolicAlert = patient.getRecords(startTime, endTime, recordType).stream()
                .anyMatch(record -> "Systolic blood pressure".equalsIgnoreCase(record.getRecordType())
                        && (record.getMeasurementValue() > 180 || record.getMeasurementValue() < 90));

        if (systolicAlert) {
            Alert alert = new BloodPressureAlertFactory().createAlert(patient.getPatientId(), "High Systolic blood pressure", System.currentTimeMillis());
            // handle alert (e.g., store or send it)
        }

        boolean diastolicAlert = patient.getRecords(startTime, endTime, recordType).stream()
                .anyMatch(record -> "Diastolic blood pressure".equalsIgnoreCase(record.getRecordType())
                        && (record.getMeasurementValue() > 120 || record.getMeasurementValue() < 60));

        if (diastolicAlert) {
            Alert alert = new BloodPressureAlertFactory().createAlert(patient.getPatientId(), "High Diastolic blood pressure", System.currentTimeMillis());
            // handle alert (e.g., store or send it)
        }
    }

    @Override
    public void checkIntervals(Patient patient, String recordType) {
        if (!recordType.equalsIgnoreCase("Systolic blood pressure") && !recordType.equalsIgnoreCase("Diastolic blood pressure")) {
            throw new IllegalArgumentException("Please provide a valid record type");
        }

        List<PatientRecord> patientRecords = patient.getRecords(startTime, endTime, recordType);
        patientRecords.stream()
                .filter(record -> {
                    double measurementValue = record.getMeasurementValue();
                    return (recordType.equalsIgnoreCase("Systolic blood pressure") && (measurementValue > 180 || measurementValue < 90))
                            || (recordType.equalsIgnoreCase("Diastolic blood pressure") && (measurementValue > 120 || measurementValue < 60));
                })
                .forEach(record -> {
                    Alert alert = new BloodPressureAlertFactory().createAlert(patient.getPatientId(), "Critical blood pressure threshold reached", record.getTimestamp());
                    // handle alert (e.g., store or send it)
                });
    }
}
