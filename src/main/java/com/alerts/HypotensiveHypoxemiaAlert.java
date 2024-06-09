package com.alerts;

import java.util.ArrayList;
import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class HypotensiveHypoxemiaAlert implements AlertCondition{
    private String alertName = "Hypotensive hypoxemia alert";

    @Override
    public List<Alert> checkCondition(Patient patient) {
        List<Alert> res = new ArrayList<>();

        List<PatientRecord> records = patient.getRecordsByType("Saturation");

        for (PatientRecord record : records) {
            if (record.getMeasurementValue() < 92) {
                long timestamp = record.getTimestamp();

                List<PatientRecord> timeFrame = patient.getRecords(timestamp - 1000, timestamp + 1000);

                for (PatientRecord check : timeFrame) {
                    if (check.getRecordType().equals("SystolicPressure")) {
                        if (check.getMeasurementValue() < 90) {
                            res.add(new Alert(Integer.toString(record.getPatientId()), alertName, timestamp));
                            break;
                        }
                    }
                }
            }
        }

        return res;
    }
    
}
