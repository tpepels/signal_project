package com.alerts;

import java.util.ArrayList;
import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class AbnormalDataAlert implements AlertCondition{
    private String alertName = "Abnormal ECG Data";
    @Override
    public List<Alert> checkCondition(Patient patient) {
        List<PatientRecord> data = patient.getRecordsByType("ECG");
        List<Alert> res = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            double sum = 0;
            int total = 0;
            for (int j = i - 5; j < i + 5; j++) {
                if (j >=0 && j < data.size()) {
                    sum += data.get(j).getMeasurementValue();
                    total++;
                }
            }
            double avg = sum/total;

            if (data.get(i).getMeasurementValue() > avg + 0.5) {
                res.add(new Alert(Integer.toString(data.get(i).getPatientId()), alertName, data.get(i).getTimestamp()));
            }
        }

        return res;
    }
    
}
