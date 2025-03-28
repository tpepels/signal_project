package com.alerts;

import java.util.ArrayList;
import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class LowSaturationAlert implements AlertCondition{
    private String alertName = "Low blood saturation alert";

    @Override
    public List<Alert> checkCondition(Patient patient) {
        List<Alert> res = new ArrayList<>();

        List<PatientRecord> records = patient.getRecordsByType("Saturation");

        for (PatientRecord record : records) {
            if (record.getMeasurementValue() < 92) {
                res.add(new Alert(Integer.toString(record.getPatientId()), alertName, record.getTimestamp()));
            }
        }

        return res;
    }
    
}
