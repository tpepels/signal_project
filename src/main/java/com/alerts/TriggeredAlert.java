package com.alerts;

import java.util.ArrayList;
import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class TriggeredAlert implements AlertCondition{
    private String alertName = "Triggered alert";

    @Override
    public List<Alert> checkCondition(Patient patient) {
        List<Alert> res = new ArrayList<>();

        List<PatientRecord> records = patient.getRecordsByType("Alert");

        for (PatientRecord alert : records) {
            if (alert.getMeasurementValue() == 0) {
                int id = alert.getPatientId();
                long timestamp = alert.getTimestamp();
                boolean resolved = false;

                for (PatientRecord resolution : records) {
                    if (alert.getMeasurementValue() == 1) {
                        if (resolution.getPatientId() == id && resolution.getTimestamp() == timestamp) {
                            resolved = true;
                            break;
                        }
                    }
                }

                if (!resolved) {
                    res.add(new Alert(Integer.toString(id), alertName, timestamp));
                }
            }
        }

        return res;
    }
    
}
