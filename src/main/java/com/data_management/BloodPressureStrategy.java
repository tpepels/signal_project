package com.data_management;

import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.alerts.BloodPressureAlertFactory;

public class BloodPressureStrategy implements AlertStrategy {
    private Patient patient;
    private Long startTime;
    private Long endTime;
    private String recordType;
    public BloodPressureStrategy(Patient patient, Long startTime, Long endTime, String recordType){
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
        this.recordType = recordType;


    }
    @Override
    public void checkAlert(Patient patient, String recordType) {
        if(!recordType.equals("Systolic Blood pressure") && !recordType.equals("Diastolic Blood pressure")){
            throw new IllegalArgumentException("Please provide the correct record type");
        }
        boolean systolicAlert = patient.getRecords(startTime, endTime, recordType).stream().anyMatch(record -> "Systolic blood pressure".equals(record.getRecordType()) && (record.getMeasurementValue() > 180 || record.getMeasurementValue() < 90));

        if(systolicAlert){
            new BloodPressureAlertFactory();
        }
        boolean diastolicAlert = patient.getRecords(startTime, endTime, recordType).stream().anyMatch(record -> "Diastolic blood pressure".equals(record.getRecordType()) && (record.getMeasurementValue() > 120 || record.getMeasurementValue() < 60));

        if(diastolicAlert){
            new BloodPressureAlertFactory();
        }

    }

    @Override
    public void checkIntervals(Patient patient, String recordType) {


    }


}
