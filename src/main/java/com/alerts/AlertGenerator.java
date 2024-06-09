package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.data_management.DataStorage;

import java.util.List;
import java.util.stream.Collectors;

public class AlertGenerator {
    private DataStorage dataStorage;

    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public void evaluateData(Patient patient) {
        long startTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000); // 24 hours ago
        long endTime = System.currentTimeMillis(); // Current time

        List<PatientRecord> bloodPressureRecords = filterBloodPressureRecords(patient.getRecords(startTime, endTime));
        List<PatientRecord> bloodOxygenRecords = filterBloodOxygenRecords(patient.getRecords(startTime, endTime));

        checkForBloodPressureTrends(patient, bloodPressureRecords);
        checkForCriticalThresholds(patient, bloodPressureRecords);
        checkForLowSaturation(patient, bloodOxygenRecords);
        checkForRapidDrop(patient, bloodOxygenRecords);
    }

    private List<PatientRecord> filterBloodPressureRecords(List<PatientRecord> records) {
        return records.stream()
                .filter(record -> "BloodPressure".equals(record.getRecordType()))
                .collect(Collectors.toList());
    }

    private List<PatientRecord> filterBloodOxygenRecords(List<PatientRecord> records) {
        return records.stream()
                .filter(record -> "BloodOxygenSaturation".equals(record.getRecordType()))
                .collect(Collectors.toList());
    }

    private void checkForBloodPressureTrends(Patient patient, List<PatientRecord> records) {
        for (int i = 2; i < records.size(); i++) {
            PatientRecord r1 = records.get(i - 2);
            PatientRecord r2 = records.get(i - 1);
            PatientRecord r3 = records.get(i);

            double diff1 = r2.getMeasurementValue() - r1.getMeasurementValue();
            double diff2 = r3.getMeasurementValue() - r2.getMeasurementValue();

            if (diff1 > 10 && diff2 > 10) {
                generateAlert(patient, "Increasing Blood Pressure Trend");
            } else if (diff1 < -10 && diff2 < -10) {
                generateAlert(patient, "Decreasing Blood Pressure Trend");
            }
        }
    }

    private void checkForCriticalThresholds(Patient patient, List<PatientRecord> records) {
        for (PatientRecord record : records) {
            double measurementValue = record.getMeasurementValue();
            if (measurementValue > 180 || measurementValue < 90 || measurementValue > 120 || measurementValue < 60) {
                generateAlert(patient, "Critical Blood Pressure Threshold");
            }
        }
    }

    private void checkForLowSaturation(Patient patient, List<PatientRecord> records) {
        for (PatientRecord record : records) {
            if ("BloodOxygenSaturation".equals(record.getRecordType())) {
                double measurementValue = record.getMeasurementValue();
                if (measurementValue < 92) {
                    generateAlert(patient, "Low Blood Oxygen Saturation");
                }
            }
        }
    }

    private void checkForRapidDrop(Patient patient, List<PatientRecord> records) {
        for (int i = 1; i < records.size(); i++) {
            PatientRecord r1 = records.get(i - 1);
            PatientRecord r2 = records.get(i);

            if ("BloodOxygenSaturation".equals(r1.getRecordType()) && "BloodOxygenSaturation".equals(r2.getRecordType())) {
                double diff = r2.getMeasurementValue() - r1.getMeasurementValue();
                long timeDiff = r2.getTimestamp() - r1.getTimestamp();

                if (diff <= -5 && timeDiff <= 600000) { // 10 minutes in milliseconds
                    generateAlert(patient, "Rapid Drop in Blood Oxygen Saturation");
                }
            }
        }
    }

    public void generateAlert(Patient patient, String condition) {
        System.out.println("place holder");
    }

    private AlertFactory getFactory(String alertType) {
        switch (alertType) {
            case "BloodPressure":
                return new BloodPressureAlertFactory();
            case "BloodOxygen":
                return new BloodOxygenAlertFactory();
            case "ECG":
                return new ECGAlertFactory();
            default:
                throw new IllegalArgumentException("Unknown alert type: " + alertType);
        }
    }

    protected void triggerAlert(Alert alert) {
        System.out.println("Alert triggered for Patient ID: " + alert.getPatientId() +
                ", Condition: " + alert.getCondition() +
                ", Timestamp: " + alert.getTimestamp());
    }
}
