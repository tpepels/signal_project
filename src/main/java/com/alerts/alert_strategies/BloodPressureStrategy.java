package com.alerts.alert_strategies;

import java.util.ArrayList;
import java.util.List;

import com.alerts.alerts.Alert;
import com.alerts.alerts.BloodPressureAlert;
import com.datamanagement.PatientRecord;


public class BloodPressureStrategy implements AlertStrategy {


    @Override
    public List<Alert> checkAlert(int patientId, List<PatientRecord> patientRecords) {
        AlertStrategyUtils records = new AlertStrategyUtils();
        List<Alert> alerts = new ArrayList<>();
        String[] types = {"SystolicPressure", "DiastolicPressure"};
        if (patientRecords.size() < 3) {
            for (String type : types) {
                for (PatientRecord patientRecord : patientRecords) {
                    if (!type.equalsIgnoreCase(patientRecord.getRecordType())) continue;
                    BloodPressureAlert alert = checkCritical(patientRecord, type, patientId);
                    if (alert != null) {
                        alerts.add(alert);
                    }
                }
            }
        }
        for (String type : types) {
            List<PatientRecord> typeRecords = records.getRecords(patientRecords, type);
            for (int i = 2; i < typeRecords.size(); i++) {

                ArrayList<PatientRecord> readings = new ArrayList<>();
                readings.add(typeRecords.get(i));
                readings.add(typeRecords.get(i - 1));
                readings.add(typeRecords.get(i - 2));

                double v0 = ((Number) readings.get(0).getMeasurementValue()).doubleValue(); // newest
                BloodPressureAlert alert0 = checkCritical(typeRecords.get(0), type, patientId);
                if (alert0 != null) {
                    alerts.add(alert0);
                }
                double v1 = ((Number) readings.get(1).getMeasurementValue()).doubleValue();
                BloodPressureAlert alert1 = checkCritical(typeRecords.get(1), type, patientId);
                if (alert1 != null) {
                    alerts.add(alert1);
                }
                double v2 = ((Number) readings.get(2).getMeasurementValue()).doubleValue(); // oldest
                BloodPressureAlert alert2 = checkCritical(typeRecords.get(2), type, patientId);
                if (alert2 != null) {
                    alerts.add(alert2);
                }
                long t0 = readings.get(0).getTimestamp();

                if (v1 - v2 > 10 && v0 - v1 > 10) {
                    alerts.add(new BloodPressureAlert(patientId,
                            type + " trends up from: " + v2 + " to " + v0,
                            t0));
                }
                if (v1 - v2 < -10 && v0 - v1 < -10) {
                    alerts.add(new BloodPressureAlert(patientId,
                            type + " trends down from: " + v2 + " to " + v0,
                            t0));
                }
            }
        }
        return alerts;
    }

    private BloodPressureAlert checkCritical(PatientRecord last, String type, int patientId) {
        try {
            double v = Double.parseDouble(last.getMeasurementValue().toString());
            long t = last.getTimestamp();
            if ("SystolicPressure".equals(type)) {
                if (v > 180)
                    return new BloodPressureAlert(patientId,
                            "Systolic Pressure Critically High: " + v,
                            t);
                if (v < 90)
                    return new BloodPressureAlert(patientId,
                            "Systolic Pressure Critically Low: " + v,
                            t);
            } else {
                if (v > 120)
                    return new BloodPressureAlert(patientId,
                            "Diastolic Pressure Critically High: " + v,
                            t);
                if (v < 60)
                    return new BloodPressureAlert(patientId,
                            "Diastolic Pressure Critically Low: " + v,
                            t);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
