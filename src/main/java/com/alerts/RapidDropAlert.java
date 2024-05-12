package com.alerts;

import java.util.ArrayList;
import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class RapidDropAlert implements AlertCondition{
    private String alertName = "Rapid blood saturation drop alert";

    @Override
    public List<Alert> checkCondition(Patient patient) {
        List<Alert> res = new ArrayList<>();

        List<PatientRecord> records = patient.getRecordsByType("Saturation");

        for (int i = 0; i < records.size(); i++) {
            long timestamp = records.get(i).getTimestamp();
            double saturation = records.get(i).getMeasurementValue();

            for (int j = i; j < records.size(); j++) {
                long currentTimestamp = records.get(j).getTimestamp();
                double currentSaturation = records.get(j).getMeasurementValue();

                if (currentTimestamp - timestamp > 600000) {
                    break;
                }

                if (saturation - currentSaturation > 5) {
                    res.add(new Alert(Integer.toString(records.get(0).getPatientId()), alertName, timestamp));
                    break;
                }
            }
        }

        return res;
    }
    
}
