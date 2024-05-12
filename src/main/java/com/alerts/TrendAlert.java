package com.alerts;

import java.util.ArrayList;
import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class TrendAlert implements AlertCondition{
    private String alertName = "Blood pressure trend alert";

    @Override
    public List<Alert> checkCondition(Patient patient) {
        List<Alert> res = new ArrayList<>();

        List<PatientRecord> records = patient.getRecordsByType("SystolicPressure");

        for (int i =  2; i < records.size(); i++) {
            long timestamp = records.get(i).getTimestamp();

            boolean increaseTrend = records.get(i - 2).getMeasurementValue() <= records.get(i - 1).getMeasurementValue() - 10 && records.get(i - 1).getMeasurementValue() <= records.get(i - 1).getMeasurementValue() - 10;
            boolean decreaseTrend = records.get(i - 2).getMeasurementValue() >= records.get(i - 1).getMeasurementValue() + 10 && records.get(i - 1).getMeasurementValue() >= records.get(i - 1).getMeasurementValue() + 10;

            if (increaseTrend || decreaseTrend) {
                res.add(new Alert(Integer.toString(records.get(0).getPatientId()), alertName, timestamp));
            }
        }

        records = patient.getRecordsByType("DiastolicPressure");

        for (int i =  2; i < records.size(); i++) {
            long timestamp = records.get(i).getTimestamp();

            boolean increaseTrend = records.get(i - 2).getMeasurementValue() <= records.get(i - 1).getMeasurementValue() - 10 && records.get(i - 1).getMeasurementValue() <= records.get(i - 1).getMeasurementValue() - 10;
            boolean decreaseTrend = records.get(i - 2).getMeasurementValue() >= records.get(i - 1).getMeasurementValue() + 10 && records.get(i - 1).getMeasurementValue() >= records.get(i - 1).getMeasurementValue() + 10;

            if (increaseTrend || decreaseTrend) {
                res.add(new Alert(Integer.toString(records.get(0).getPatientId()), alertName, timestamp));
            }
        }

        return res;
    }    
}
