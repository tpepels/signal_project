package com.alerts.alert_strategies;

import com.alerts.alerts.Alert;
import com.alerts.alerts.TriggeredAlert;
import com.datamanagement.PatientRecord;

import java.util.ArrayList;
import java.util.List;


public class TriggeredAlertStrategyStrategy implements AlertStrategy {
    @Override
    public List<Alert> checkAlert(int patientID, List<PatientRecord> patientRecords) {
        AlertStrategyUtils patient = new AlertStrategyUtils();
        List<Alert> alerts = new ArrayList<>();
        String type = "Alert";
        List<PatientRecord> records = patient.getRecords(patientRecords, type);
        for (PatientRecord record : records) {
            if(record.getMeasurementValue().toString().equalsIgnoreCase(type))
                alerts.add(new TriggeredAlert(patientID, "Alert " + record.getMeasurementValue().toString(), record.getTimestamp()));
        }
        return alerts;
    }
}
