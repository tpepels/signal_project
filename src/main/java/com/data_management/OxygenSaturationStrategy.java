package com.data_management;

import com.alerts.Alert;
import com.alerts.BloodOxygenAlertFactory;

import java.util.List;
import java.util.stream.IntStream;

public class OxygenSaturationStrategy implements AlertStrategy {
    private final Patient patient;
    private final Long startTime;
    private final Long endTime;
    private static final String RECORD_TYPE = "Oxygen Saturation";

    public OxygenSaturationStrategy(Patient patient, Long startTime, Long endTime) {
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public void checkAlert(Patient patient, String recordType) {
        if (!recordType.equalsIgnoreCase(RECORD_TYPE)) {
            throw new IllegalArgumentException("Please provide the correct record type");
        }

        for (PatientRecord record : patient.getRecords(startTime, endTime, recordType)) {
            if (record.getMeasurementValue() < 92) {
                Alert alert = new BloodOxygenAlertFactory().createAlert(patient.getPatientId(), "Low Oxygen Saturation", record.getTimestamp());
                // Handle the alert (e.g., log, store, send it)
            }
        }
    }

    @Override
    public void checkIntervals(Patient patient, String recordType) {
        if (!recordType.equalsIgnoreCase(RECORD_TYPE)) {
            throw new IllegalArgumentException("Please provide the correct record type");
        }

        List<PatientRecord> patientRecords = patient.getRecords(startTime, endTime, recordType);

        IntStream.range(0, patientRecords.size()).forEach(i -> {
            PatientRecord currentRecord = patientRecords.get(i);
            long currentTime = currentRecord.getTimestamp();
            double currentValue = currentRecord.getMeasurementValue();

            boolean alertTriggered = patientRecords.stream()
                    .skip(i + 1)
                    .takeWhile(nextRecord -> nextRecord.getTimestamp() <= currentTime + 10 * 60 * 1000)
                    .anyMatch(nextRecord -> nextRecord.getMeasurementValue() <= currentValue * 0.95);

            if (alertTriggered) {
                PatientRecord alertRecord = patientRecords.stream()
                        .skip(i + 1)
                        .filter(nextRecord -> nextRecord.getMeasurementValue() <= currentValue * 0.95)
                        .findFirst()
                        .orElse(null);

                if (alertRecord != null) {
                    Alert alert = new BloodOxygenAlertFactory().createAlert(patient.getPatientId(), "Blood oxygen dropped 5% or more", alertRecord.getTimestamp());
                    // Handle the alert (e.g., log, store, send it)
                }
            }
        });
    }
}
