package com.alerts;

import com.data_management.*;

import java.util.List;

/**
 * HeartRateStrategy evaluates a patient's heart rate data to determine if it falls
 * outside a safe range. Specifically, it checks if the heart rate is below 50 bpm
 * or above 120 bpm, which are considered abnormal conditions.
 *
 * This strategy implements the AlertStrategy interface and returns an Alert
 * when a violation is detected.
 */
public class HeartRateStrategy implements AlertStrategy {

    /**
     * Evaluates the heart rate records for a given patient and checks if any reading
     * is outside the normal range of 50 to 120 beats per minute.
     *
     * If an abnormal heart rate is found, an alert is generated immediately for
     * that reading.
     *
     * @param patient the patient whose heart rate data is being evaluated
     * @return an Alert if an abnormal heart rate is detected, or an Alert with
     *         condition "none" if no issues are found
     */
    @Override
    public Alert checkAlert(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);

        for (PatientRecord record : records) {
            if (record.getRecordType().equalsIgnoreCase("HeartRate")) {
                double value = record.getMeasurementValue();
                if (value < 50 || value > 120) {
                    return new Alert(
                        String.valueOf(patient.getPatientId()),
                        "Abnormal Heart Rate: " + value,
                        record.getTimestamp()
                    );
                }
            }
        }

        return new Alert(String.valueOf(patient.getPatientId()), "none", System.currentTimeMillis());
    }
}
