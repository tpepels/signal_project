package com.data_management;
import com.alerts.Alert;
import com.alerts.ECGAlertFactory;

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
        for(PatientRecord record : patient.getRecords(startTime, endTime, recordType)){
            if(record.getMeasurementValue() > 100 || record.getMeasurementValue() < 60){
                new ECGAlertFactory();
            }
        }
    }
}

