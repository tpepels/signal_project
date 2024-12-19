package com.strategies;

import com.data_management.PatientRecord;

/**
 * The {@code HeartRateStrategy} class implements the {@link AlertStrategy} interface.
 * It defines alert conditions for heart rate readings, specifically if the heart rate is
 * below 50 beats per minute or above 100 beats per minute.
 */
public class HeartRateStrategy implements AlertStrategy {

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
}
