package com.alerts;
import com.data_management.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Strategy for evaluating blood pressure records of a patient.
 * This strategy checks for critical thresholds and consistent trends in both systolic
 * and diastolic blood pressure readings.
 *
 * If a threshold breach or a consistent increase/decrease is detected, an Alert is triggered.
 * The evaluation is based on the most recent records sorted by timestamp.
 */
public class BloodPressureStrategy implements AlertStrategy {

    /**
     * Evaluates a patient's blood pressure data for alert conditions.
     * This includes checking both systolic and diastolic readings for:
     * - Critical values beyond acceptable thresholds
     * - Three-point increasing or decreasing trends
     *
     * @param patient the patient whose data is being analyzed
     * @return an Alert object if any condition is met, or an alert with "none" if all readings are normal
     */
    @Override
    public Alert checkAlert(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);
        List<PatientRecord> systolic = new ArrayList<>();
        List<PatientRecord> diastolic = new ArrayList<>();

        for (PatientRecord record : records) {
            String type = record.getRecordType();
            if (type.equalsIgnoreCase("SystolicBloodPressure")) {
                systolic.add(record);
            } else if (type.equalsIgnoreCase("DiastolicBloodPressure")) {
                diastolic.add(record);
            }
        }

        Alert systolicAlert = checkTrendAndThreshold(systolic, "SystolicBloodPressure", patient.getPatientId(), 90, 180);
        if (!systolicAlert.getCondition().equals("none")) return systolicAlert;

        Alert diastolicAlert = checkTrendAndThreshold(diastolic, "DiastolicBloodPressure", patient.getPatientId(), 60, 120);
        if (!diastolicAlert.getCondition().equals("none")) return diastolicAlert;

        return new Alert(String.valueOf(patient.getPatientId()), "none", System.currentTimeMillis());
    }

    /**
     * Checks a list of patient records for threshold violations or trends.
     * The method verifies if any value falls below or exceeds defined safe limits.
     * It also checks for consistent upward or downward trends in values over three consecutive entries.
     *
     * @param records the filtered list of blood pressure records to evaluate
     * @param type the measurement type being checked, such as systolic or diastolic
     * @param patientId the ID of the patient whose data is under evaluation
     * @param min the minimum acceptable value for the measurement
     * @param max the maximum acceptable value for the measurement
     * @return an Alert describing the condition if found, or an alert with "none"
     */
    private Alert checkTrendAndThreshold(List<PatientRecord> records, String type, int patientId, double min, double max) {
        records.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        for (PatientRecord record : records) {
            double value = record.getMeasurementValue();
            if (value < min || value > max) {
                return new Alert(String.valueOf(patientId), "Critical " + type + ": " + value, record.getTimestamp());
            }
        }

        for (int i = 2; i < records.size(); i++) {
            double first = records.get(i - 2).getMeasurementValue();
            double second = records.get(i - 1).getMeasurementValue();
            double third = records.get(i).getMeasurementValue();

            boolean increasing = (second - first > 10) && (third - second > 10);
            boolean decreasing = (first - second > 10) && (second - third > 10);

            if (increasing) {
                return new Alert(String.valueOf(patientId), "Increasing Trend in " + type, records.get(i).getTimestamp());
            }
            if (decreasing) {
                return new Alert(String.valueOf(patientId), "Decreasing Trend in " + type, records.get(i).getTimestamp());
            }
        }

        return new Alert("", "none", 0);
    }
}
