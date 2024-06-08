package com.data_management;

import com.alerts.Alert;
import com.alerts.AlertGenerator;

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
        if(!recordType.equals("Blood pressure")){
            throw new IllegalArgumentException("Please provide the correct record type");
        }
        for(PatientRecord record : patient.getRecords(startTime, endTime, recordType)){
            if(record.getMeasurementValue() >= 10 || record.getMeasurementValue() < 4){
                //AlertGenerator generator = new AlertGenerator();
            }
        }
    }

}
