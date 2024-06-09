package com.data_management;

import com.alerts.Alert;
import com.alerts.BloodOxygenAlertFactory;

public class OxygenSaturationStrategy implements AlertStrategy {
    private Patient patient;
    private Long startTime;
    private Long endTime;
    private final String recordType = "Oxygen Saturation";

    public OxygenSaturationStrategy(Patient patient, Long startTime, Long endTime) {
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
    }



    @Override
    public void checkAlert(Patient patient, String recordType) {
        if (!recordType.equals("Oxygen Saturation")) {
            throw new IllegalArgumentException("Please provide the correct record");
        }
        for (PatientRecord record : patient.getRecords(startTime, endTime, recordType)) {
            if (record.getMeasurementValue() < 92) {
                new BloodOxygenAlertFactory();
            }

        }
    }

    @Override
    public void checkIntervals(Patient patient, String recordType) {

    }
}
