package com.alerts;
import com.data_management.PatientRecord;

import java.util.List;

public class BloodPressureStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(List<PatientRecord> records) {
        return records.stream()
                .filter(r -> r.getRecordType().equals("SystolicBP"))
                .anyMatch(r -> r.getMeasurementValue() < 90 || r.getMeasurementValue() > 180);
    }

    @Override
    public String getCondition() {
        return "Abnormal Systolic Blood Pressure";
    }


}



