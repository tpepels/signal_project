package com.data_management;

import com.alerts.Alert;
import com.alerts.ECGAlertFactory;

import java.util.List;
import java.util.stream.IntStream;

public class HeartRateStrategy implements AlertStrategy {
    private final Patient patient;
    private final Long startTime;
    private final Long endTime;
    private static final String RECORD_TYPE = "Heart Rate";

    public HeartRateStrategy(Patient patient, Long startTime, Long endTime) {
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public void checkAlert(Patient patient, String recordType) {
        if (!recordType.equalsIgnoreCase(RECORD_TYPE)) {
            throw new IllegalArgumentException("Please provide the correct argument");
        }
        for (PatientRecord record : patient.getRecords(startTime, endTime, recordType)) {
            if (record.getMeasurementValue() > 100 || record.getMeasurementValue() < 60) {
                Alert alert = new ECGAlertFactory().createAlert(patient.getPatientId(), "Abnormal Heart Rate", record.getTimestamp());
                // Handle the alert (e.g., log, store, or send it)
            }
        }
    }

    @Override
    public void checkIntervals(Patient patient, String recordType) {
        if (!recordType.equalsIgnoreCase(RECORD_TYPE)) {
            throw new IllegalArgumentException("Please provide the correct argument");
        }

        List<PatientRecord> records = patient.getRecords(startTime, endTime, recordType);

        // Defining the sliding window size
        int windowSize = 5;

        // Use IntStream from stream library to create indices
        IntStream.range(0, records.size())
                .forEach(i -> {
                    // Determine the window range
                    int fromIndex = Math.max(0, i - windowSize + 1);
                    int toIndex = i + 1;

                    // Calculate the average within the window
                    double average = records.subList(fromIndex, toIndex).stream().mapToDouble(PatientRecord::getMeasurementValue).average().orElse(0);

                    // Checking for abnormal data
                    PatientRecord currentRecord = records.get(i);
                    double currentValue = currentRecord.getMeasurementValue();
                    if (currentValue > average * 1.5) { // Example threshold: 50% above average
                        Alert alert = new ECGAlertFactory().createAlert(patient.getPatientId(), "Abnormal ECG Data", currentRecord.getTimestamp());
                        // Handle the alert (e.g., log, store, send it)
                    }
                });
    }
}


