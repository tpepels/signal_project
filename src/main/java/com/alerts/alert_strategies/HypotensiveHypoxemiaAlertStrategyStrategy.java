package com.alerts.alert_strategies;

import com.alerts.alerts.Alert;
import com.alerts.alerts.HypotensiveHypoxemiaAlert;
import com.datamanagement.PatientRecord;

import java.util.ArrayList;
import java.util.List;

import static com.alerts.alert_strategies.AlertStrategyUtils.parseDouble;

public class HypotensiveHypoxemiaAlertStrategyStrategy implements AlertStrategy {

    @Override
    public List<Alert> checkAlert(int patientID, List<PatientRecord> patientRecords) {
        AlertStrategyUtils record = new AlertStrategyUtils();
        List<Alert> alerts = new ArrayList<>();
        // 1) most recent SystolicPressure
        List<PatientRecord> records = record.getRecords(patientRecords, "SystolicPressure", "Saturation");

        if (records.size() <= 1) return alerts;  // missing data
        for(PatientRecord patientRecord : records) {
            evaluate(patientRecord, alerts, records, patientID);
            }
        return alerts;
    }

    public void evaluate(PatientRecord record, List<Alert> alerts, List<PatientRecord> records,  int patientID) {
            String pressure = "SystolicPressure";
            String saturation = "Saturation";
            String type = record.getRecordType();
            String toCompare;
            double value = 0;
            double value2 = 0;
            if(type.equalsIgnoreCase(pressure)) {value = 90; value2 = 92; type = pressure; toCompare = saturation;}
            else {value = 92; value2= 90; type = saturation; toCompare = pressure;}
            if(record.getRecordType().equalsIgnoreCase(type)&&
                    parseDouble(record.getMeasurementValue().toString())<value) {
                long t0 = record.getTimestamp();
                for(Alert alert : alerts) {
                    if(alert.getTimestamp()>t0 &&
                            alert.getTimestamp()<(t0+60000) &&
                            record.getRecordType().equalsIgnoreCase(toCompare) &&
                            parseDouble(record.getMeasurementValue().toString())<value2) {
                        alerts.add(new HypotensiveHypoxemiaAlert(patientID,
                                "HypotensiveHypoxemia", record.getTimestamp()));
                    }
                }
            }
    }
}
