package com.data_management;
import com.alerts.Alert;
import com.alerts.ECGAlertFactory;

import java.util.List;
import java.util.stream.IntStream;

public class HeartRateStrategy implements AlertStrategy {
    private Patient patient;
    private Long startTime;
    private Long endTime;
    private final String recordType = "Heart Rate";

    public HeartRateStrategy(Patient patient, Long startTime, Long endTime) {
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public void checkAlert(Patient patient, String recordType) {
        if(!recordType.equals("Heart Rate")){
            throw new IllegalArgumentException("Please provide the correct argument");
        }
        for(PatientRecord record : patient.getRecords(startTime, endTime, recordType)){
            if(record.getMeasurementValue() > 100 || record.getMeasurementValue() < 60){
                new ECGAlertFactory();
            }
        }
    }

    @Override
    public void checkIntervals(Patient patient, String recordType) {
        if (!recordType.equals("Heart Rate")) {
            throw new IllegalArgumentException("Please provide the correct argument");
        }

        List<PatientRecord> records = patient.getRecords(startTime, endTime, recordType);

        // Define the sliding window size
        int windowSize = 5;

        // Use IntStream to create indices and process the sliding window
        IntStream.range(0, records.size())
                .forEach(i -> {
                    // Determine the window range
                    int fromIndex = Math.max(0, i - windowSize + 1);
                    int toIndex = i + 1;

                    // Calculate the average within the  window
                    double average = records.subList(fromIndex, toIndex).stream().mapToDouble(PatientRecord::getMeasurementValue).average().orElse(0);
                    // Checking for abnormal data
                   PatientRecord currentRecord = records.get(i);
                    double currentValue = currentRecord.getMeasurementValue();
                    if (currentValue > average * 1.5) { // Example threshold: 50% above average
                        Alert alert = new ECGAlertFactory().createAlert(patient.getPatientId(), "Abnormal ECG Data", currentRecord.getTimestamp());}
                });
    }
}

