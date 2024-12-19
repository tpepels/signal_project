package com.strategies;

import com.data_management.PatientRecord;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The {@code BloodPressureStrategy} class implements the {@link AlertStrategy} interface.
 * It defines alert conditions for blood pressure readings, including critical thresholds
 * and trends in blood pressure measurements.
 */
public class BloodPressureStrategy implements AlertStrategy {
    private static class BloodPressureMeasurement {
        double systolic;
        double diastolic;

        BloodPressureMeasurement(double systolic, double diastolic) {
            this.systolic = systolic;
            this.diastolic = diastolic;
        }
    }

    private final Queue<BloodPressureMeasurement> measurements = new LinkedList<>();

    /**
     * Checks whether the given patient record meets the criteria for triggering an alert.
     * The criteria include critical threshold values and trends in blood pressure measurements.
     *
     * @param record the patient record to check
     * @return {@code true} if the record meets the alert criteria, {@code false} otherwise
     */
    @Override
    public boolean checkAlert(PatientRecord record) {
        double[] bpValues = record.getMeasurementValues();
        double systolic = bpValues[0];
        double diastolic = bpValues[1];

        // Add current measurement to the queue
        measurements.add(new BloodPressureMeasurement(systolic, diastolic));

        // Keep only the last 4 measurements
        if (measurements.size() > 4) {
            measurements.poll();
        }

        boolean criticalThresholdAlert = systolic > 180 || systolic < 90 || diastolic > 120 || diastolic < 60;
        boolean trendAlert = checkIncreasingSystolicTrend() || checkDecreasingSystolicTrend() ||
                checkIncreasingDiastolicTrend() || checkDecreasingDiastolicTrend();

        return criticalThresholdAlert || trendAlert;
    }

    /**
     * Checks whether the given patient record meets the criteria for critical threshold alerts.
     * Critical threshold values are defined as systolic > 180, systolic < 90, diastolic > 120, or diastolic < 60.
     *
     * @param record the patient record to check
     * @return {@code true} if the record meets the critical threshold alert criteria, {@code false} otherwise
     */
    public boolean checkCriticalThresholdAlert(PatientRecord record) {
        double[] bpValues = record.getMeasurementValues();
        double systolic = bpValues[0];
        double diastolic = bpValues[1];
        return systolic > 180 || systolic < 90 || diastolic > 120 || diastolic < 60;
    }

    /**
     * Checks for an increasing trend in systolic blood pressure over the last 4 measurements.
     * An increasing trend is defined as each successive measurement being at least 10 mmHg higher than the previous one.
     *
     * @return {@code true} if there is an increasing trend in systolic blood pressure, {@code false} otherwise
     */
    public boolean checkIncreasingSystolicTrend() {
        if (measurements.size() < 4) return false;

        BloodPressureMeasurement[] lastFour = measurements.toArray(new BloodPressureMeasurement[0]);
        return lastFour[1].systolic > lastFour[0].systolic + 10 &&
                lastFour[2].systolic > lastFour[1].systolic + 10 &&
                lastFour[3].systolic > lastFour[2].systolic + 10;
    }

    /**
     * Checks for a decreasing trend in systolic blood pressure over the last 4 measurements.
     * A decreasing trend is defined as each successive measurement being at least 10 mmHg lower than the previous one.
     *
     * @return {@code true} if there is a decreasing trend in systolic blood pressure, {@code false} otherwise
     */
    public boolean checkDecreasingSystolicTrend() {
        if (measurements.size() < 4) return false;

        BloodPressureMeasurement[] lastFour = measurements.toArray(new BloodPressureMeasurement[0]);
        return lastFour[1].systolic < lastFour[0].systolic - 10 &&
                lastFour[2].systolic < lastFour[1].systolic - 10 &&
                lastFour[3].systolic < lastFour[2].systolic - 10;
    }

    /**
     * Checks for an increasing trend in diastolic blood pressure over the last 4 measurements.
     * An increasing trend is defined as each successive measurement being at least 10 mmHg higher than the previous one.
     *
     * @return {@code true} if there is an increasing trend in diastolic blood pressure, {@code false} otherwise
     */
    public boolean checkIncreasingDiastolicTrend() {
        if (measurements.size() < 4) return false;

        BloodPressureMeasurement[] lastFour = measurements.toArray(new BloodPressureMeasurement[0]);
        return lastFour[1].diastolic > lastFour[0].diastolic + 10 &&
                lastFour[2].diastolic > lastFour[1].diastolic + 10 &&
                lastFour[3].diastolic > lastFour[2].diastolic + 10;
    }

    /**
     * Checks for a decreasing trend in diastolic blood pressure over the last 4 measurements.
     * A decreasing trend is defined as each successive measurement being at least 10 mmHg lower than the previous one.
     *
     * @return {@code true} if there is a decreasing trend in diastolic blood pressure, {@code false} otherwise
     */
    public boolean checkDecreasingDiastolicTrend() {
        if (measurements.size() < 4) return false;

        BloodPressureMeasurement[] lastFour = measurements.toArray(new BloodPressureMeasurement[0]);
        return lastFour[1].diastolic < lastFour[0].diastolic - 10 &&
                lastFour[2].diastolic < lastFour[1].diastolic - 10 &&
                lastFour[3].diastolic < lastFour[2].diastolic - 10;
    }
}
