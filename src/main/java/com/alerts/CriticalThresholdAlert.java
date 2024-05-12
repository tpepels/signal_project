package com.alerts;

import java.util.ArrayList;
import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class CriticalThresholdAlert implements AlertCondition{
    String alertName = "Blood pressure critical threshold alert";

    @Override
    public List<Alert> checkCondition(Patient patient) {
        List<Alert> res = new ArrayList<>();

        List<PatientRecord> records = patient.getRecordsByType("SystolicPressure");
        double upperThreshold = 180;
        double lowerThreshold = 90;

        for (PatientRecord rec : records) {
            if (rec.getMeasurementValue() > upperThreshold || rec.getMeasurementValue() < lowerThreshold) {
                res.add(new Alert(Integer.toString(rec.getPatientId()), alertName, rec.getTimestamp()));
            }
        }

        records = patient.getRecordsByType("DiastolicPressure");
        upperThreshold = 120;
        lowerThreshold = 60;

        for (PatientRecord rec : records) {
            if (rec.getMeasurementValue() > upperThreshold || rec.getMeasurementValue() < lowerThreshold) {
                res.add(new Alert(Integer.toString(rec.getPatientId()), alertName, rec.getTimestamp()));
            }
        }

        return res;
    }
    
}
