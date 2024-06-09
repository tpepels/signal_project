package com.data_management;
import com.alerts.Alert;
import com.alerts.ECGAlertFactory;

import java.util.List;

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
        int iterations = 0;
       List<PatientRecord> patientRec = patient.getRecords(startTime, endTime, recordType);
       for(int i = 0; i<patientRec.size();i++){
           System.out.println("place holder");
       }


    }
}

