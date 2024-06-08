package com.data_management;

import com.alerts.Alert;
import com.alerts.BloodOxygenAlertFactory;

public class OxygenSaturationStrategy implements AlertStrategy {
    private Patient patient;
    private Long startTime;
    private Long endTime;
    private String recordType;

    public OxygenSaturationStrategy(Patient patient, Long startTime, Long endTime, String recordType) {
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
        this.recordType = recordType;
    }

    @Override
    public void checkAlert(Patient patient, String recordType) {
        if (!recordType.equals("Oxygen Saturation")) {
            throw new IllegalArgumentException("Please provide the correct record");
        }
        for (PatientRecord record : patient.getRecords(startTime, endTime, recordType)) {
            if (record.getMeasurementValue() < 76) {
                new BloodOxygenAlertFactory();
            }

        }
    }
}
