package com.data_management;
import com.alerts.Alert;

public class HeartRateStrategy implements AlertStrategy {
    private Patient patient;
    private Long startTime;
    private Long endTime;
    private String recordType;

    public HeartRateStrategy(Patient patient, Long startTime, Long endTime, String recordType) {
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
        this.recordType = recordType;
    }

    @Override
    public void checkAlert(Patient patient, String recordType) {
        if(!recordType.equals("Heart Rate")){
            throw new IllegalArgumentException("Please provide the correct argument");
        }
        patient.getRecords(startTime, endTime, recordType);
    }
}

