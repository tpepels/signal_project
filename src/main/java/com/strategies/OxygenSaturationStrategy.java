package com.strategies;

import com.data_management.PatientRecord;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The {@code OxygenSaturationStrategy} class implements the {@link AlertStrategy} interface.
 * It defines alert conditions for blood oxygen saturation levels, including low saturation and rapid drops.
 */
public class OxygenSaturationStrategy implements AlertStrategy {

    /**
     * Inner class to represent a measurement with a timestamp and a value.
     */
    private static class Measurement {
        LocalDateTime timestamp;
        double value;

        /**
         * Constructs a new {@code Measurement}.
         *
         * @param timestamp the time at which the measurement was taken
         * @param value     the measured value
         */
        Measurement(LocalDateTime timestamp, double value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    private final Queue<Measurement> measurements = new LinkedList<>();

    /**
     * Checks whether the given patient record meets the criteria for triggering an alert.
     * The criteria include blood oxygen saturation levels below 92% and rapid drops of 5% or more within a 10-minute window.
     *
     * @param record the patient record to check
     * @return {@code true} if the record meets the alert criteria, {@code false} otherwise
     */
    @Override
    public boolean checkAlert(PatientRecord record) {
        double currentSaturation = record.getMeasurementValue();
        LocalDateTime currentTime = LocalDateTime.now();

        // Remove measurements older than 10 minutes
        while (!measurements.isEmpty() && measurements.peek().timestamp.isBefore(currentTime.minusMinutes(10))) {
            measurements.poll();
        }

        boolean lowSaturationAlert = currentSaturation < 92;
        boolean rapidDropAlert = checkRapidDropAlert(currentSaturation);

        // Add the current measurement to the list
        measurements.add(new Measurement(currentTime, currentSaturation));

        return lowSaturationAlert || rapidDropAlert;
    }

    /**
     * Checks if the given patient record indicates a low saturation alert.
     *
     * @param record the patient record to check
     * @return {@code true} if the saturation level is below 92%, {@code false} otherwise
     */
    public boolean isLowSaturationAlert(PatientRecord record) {
        return record.getMeasurementValue() < 92;
    }

    /**
     * Checks if the given patient record indicates a rapid drop in saturation.
     *
     * @param record the patient record to check
     * @return {@code true} if there is a rapid drop in saturation, {@code false} otherwise
     */
    public boolean isRapidDropAlert(PatientRecord record) {
        double currentSaturation = record.getMeasurementValue();
        return checkRapidDropAlert(currentSaturation);
    }

    /**
     * Checks if there has been a rapid drop in saturation compared to previous measurements.
     * A rapid drop is defined as a decrease of 5% or more within the last 10 minutes.
     *
     * @param currentSaturation the current saturation value
     * @return {@code true} if there is a rapid drop, {@code false} otherwise
     */
    private boolean checkRapidDropAlert(double currentSaturation) {
        for (Measurement measurement : measurements) {
            if (measurement.value - currentSaturation >= 5) {
                return true;
            }
        }
        return false;
    }
}
