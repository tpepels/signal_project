package com.alerts.Strategy;
import java.util.*;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public class BloodPressureStrategy implements AlertStrategy {
    private String type;  // Type can be "Systolic" or "Diastolic"

    public BloodPressureStrategy(String type) {
        this.type = type;
    }

    @Override
    public boolean checkAlert(Patient patient) {
        List<PatientRecord> lastThreeRecords = getLastThreeBloodPressure(patient, type);
        if (lastThreeRecords.size() < 3) {
            return false;  // Not enough data to check the condition
        }

        // Extract records assuming they are returned in chronological order
        PatientRecord record1 = lastThreeRecords.get(0);
        PatientRecord record2 = lastThreeRecords.get(1);
        PatientRecord record3 = lastThreeRecords.get(2);

        // Apply the blood pressure check logic
        return checkBloodPressure(record1, record2, record3, type);
    }

    private boolean checkBloodPressure(PatientRecord record1, PatientRecord record2, PatientRecord record3, String type) {
        // Trend alert
        if (record2.getMeasurementValue() >= record1.getMeasurementValue() + 10 && record3.getMeasurementValue() >=
                record2.getMeasurementValue() + 10) {
            return true;
        } else if (record2.getMeasurementValue() <= record1.getMeasurementValue() - 10 && record3.getMeasurementValue() <=
                record2.getMeasurementValue() - 10) {
            return true;
        }

        // Critical threshold alert
        if (type.equals("Systolic")) {
            if (record3.getMeasurementValue() > 180 || record3.getMeasurementValue() < 90) {
                return true;
            }
        } else {  // Diastolic case
            if (record3.getMeasurementValue() > 120 || record3.getMeasurementValue() < 60) {
                return true;
            }
        }
        return false;
    }

    private List<PatientRecord> getLastThreeBloodPressure(Patient patient, String type) {
        List<PatientRecord> patientRecordList = patient.getRecords(patient.getStartTime(), patient.getEndTime());
        List<PatientRecord> lastThree = new ArrayList<>();
        int count = 0;  // To keep track of how many matching records we've added

        // Iterate backwards through the patientRecordList
        for (int i = patientRecordList.size() - 1; i >= 0 && count < 3; i--) {
            PatientRecord record = patientRecordList.get(i);
            if (record.getRecordType().equals(type)) {
                lastThree.add(0, record);  // Add in reverse to ensure chronological order
                count++;
            }
        }
        return lastThree;
    }


}

