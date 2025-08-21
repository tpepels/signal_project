package com.alerts.alert_strategies;

import java.util.*;

import com.alerts.alerts.Alert;
import com.alerts.alerts.BloodOxygenAlert;
import com.datamanagement.PatientRecord;

import static com.alerts.alert_strategies.AlertStrategyUtils.parseDouble;

public class OxygenSaturationStrategy implements AlertStrategy {

    private static final String saturation = "Saturation";


    @Override
    public List<Alert> checkAlert(int patientId, List<PatientRecord> patientRecords) {
        AlertStrategyUtils record = new AlertStrategyUtils();
        List<PatientRecord> records = record.getRecords(patientRecords, saturation);
        List<Alert> alerts = new ArrayList<>();
        for(int i = 0; i < records.size(); i++) {
            checkCritical(patientId, records.get(i), alerts);
            evaluateTrend(patientId, records, alerts, i);
        }
        return List.of();
    }

    private void evaluateTrend(int patientId, List<PatientRecord> records, List<Alert> alerts, int idx) {
        long timeFrame = 60000;
        double saturation = parseDouble(records.get(idx).getMeasurementValue().toString());
        long t0 = records.get(idx).getTimestamp();
        for(int i = idx +1; i < records.size(); i++) {
            if(records.get(i).getTimestamp() > t0+timeFrame) {break;}
            double saturation2 = parseDouble(records.get(i).getMeasurementValue().toString());
            if(saturation*0.95>saturation2) {
                long ts = records.get(i).getTimestamp();
                alerts.add(new BloodOxygenAlert(patientId, "Rapid Saturation drop from : " + saturation + " to " + saturation2, ts));
            }
        }


    }

    private void checkCritical(int patientId, PatientRecord record, List<Alert> alerts) {
        double value = parseDouble(record.getMeasurementValue().toString());
        if(value < 92) alerts.add(new BloodOxygenAlert(patientId, "Saturation critically low: " + value + " ", record.getTimestamp()));
    }
}
