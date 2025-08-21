package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class ECGStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(List<PatientRecord> records) {
        return records.stream()
                .filter(r -> r.getRecordType().equals("ECG"))
                .anyMatch(r -> r.getMeasurementValue() > 2.5);
    }

    @Override
    public String getCondition() {
        return "Abnormal ECG Data";
    }
}