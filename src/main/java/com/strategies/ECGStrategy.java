package com.strategies;

import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ECGStrategy} class implements the {@link AlertStrategy} interface.
 * It defines alert conditions for electrocardiogram (ECG) readings, including heart rate thresholds
 * and irregular heartbeat patterns.
 */
public class ECGStrategy implements AlertStrategy {

    /**
     * Checks whether the given patient record meets the criteria for triggering an alert.
     * The criteria include heart rates below 50 beats per minute or above 100 beats per minute.
     *
     * @param record the patient record to check
     * @return {@code true} if the record meets the alert criteria, {@code false} otherwise
     */
    @Override
    public boolean checkAlert(PatientRecord record) {
        double heartRate = record.getMeasurementValue();
        return heartRate < 50 || heartRate > 100;
    }

    /**
     * Checks for irregular heartbeat patterns in the given list of patient records.
     * An irregular heartbeat is identified based on the time intervals between successive heartbeats,
     * with a tolerance of 200 milliseconds from the expected interval calculated based on the average beats per minute.
     *
     * @param records a list of patient records to check for irregular heartbeat patterns
     * @return {@code true} if an irregular heartbeat pattern is detected, {@code false} otherwise
     */
    public boolean checkIrregularBeat(List<PatientRecord> records) {
        List<Long> timestamps = new ArrayList<>();
        List<Double> heartRates = new ArrayList<>();
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("HeartRate")) {
                heartRates.add(record.getMeasurementValue());
                timestamps.add(record.getTimestamp());
            }
        }

        for (int i = 1; i < timestamps.size(); i++) {
            long interval = timestamps.get(i) - timestamps.get(i - 1);
            if (interval != 0) {
                if (Math.abs(interval - 60000 / heartRates.get(i)) > 200) {
                    return true;
                }
            }
        }
        return false;
    }
}
