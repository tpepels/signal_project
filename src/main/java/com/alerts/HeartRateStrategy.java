package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class HeartRateStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(List<PatientRecord> records) {
        return records.stream()
                .filter(r -> r.getRecordType().equals("Heart Rate"))
                .anyMatch(r -> r.getMeasurementValue() < 60 || r.getMeasurementValue() > 120);
    }

    @Override
    public String getCondition() {
        return "Abnormal Heart Rate";
    }
}
