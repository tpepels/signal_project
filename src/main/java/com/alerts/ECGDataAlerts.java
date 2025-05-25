package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * The {@code ECGDataAlerts} class detects abnormal peaks in ECG readings using a sliding window approach.
 *
 * This class implements the {@link AlertTester} interface and checks for values that exceed
 * a dynamic threshold based on the average of the last N ECG readings.
 *
 * Functionality:
 * 
 *     Maintains a sliding window of the most recent {@code WINDOW_SIZE} ECG readings.
 *     Calculates the average value in the window.
 *     If the current ECG value exceeds the average × {@code THRESHOLD_MULTIPLIER}, an alert is triggered.
 * 
 *
 * Assumptions:
 * 
 *    Only records labeled as "ECG" (case-insensitive) are considered.
 *     Readings are sorted chronologically before processing.
 *     Abnormality detection only begins after accumulating enough readings to fill the window.
 *     The checker retains internal state (window and sum) between evaluations, which is acceptable
 *         for single-use objects but may not be thread-safe for shared use.
 * 
 */
public class ECGDataAlerts implements AlertTester {

    private static final int WINDOW_SIZE = 5; // Number of readings in the moving window
    private static final double THRESHOLD_MULTIPLIER = 1.5; // Multiplier for peak detection
    private final LinkedList<Double> window = new LinkedList<>();
    private double windowSum = 0;

    /**
     * Checks ECG records for abnormal peaks based on sliding window average analysis.
     *
     * If a patient's ECG value exceeds the current window's average x 1.5,
     * an alert is generated with timestamp and condition details.
     *
     * @param patient the patient whose ECG data is being evaluated
     * @return an {@link Alert} if an abnormal peak is detected, otherwise an alert with condition {@code "none"}
     */
    @Override
    public Alert check(Patient patient) {
        List<PatientRecord> allRecords = patient.getRecords(0, Long.MAX_VALUE);
        List<PatientRecord> ecgRecords = new ArrayList<>();

  
        for (PatientRecord record : allRecords) {
            if (record.getRecordType().equalsIgnoreCase("ECG")) {
                ecgRecords.add(record);
            }
        }

       
        ecgRecords.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        for (PatientRecord record : ecgRecords) {
            double value = record.getMeasurementValue();

          
            if (window.size() == WINDOW_SIZE) {
                windowSum -= window.removeFirst();
            }

            window.addLast(value);
            windowSum += value;

            if (window.size() == WINDOW_SIZE) {
                double average = windowSum / WINDOW_SIZE;
                double threshold = average * THRESHOLD_MULTIPLIER;

                if (value > threshold) {
                    return new Alert(
                            String.valueOf(patient.getPatientId()),
                            "Abnormal ECG peak detected: " + value + " (avg: " + average + ")",
                            record.getTimestamp()
                    );
                }
            }
        }

        return new Alert(String.valueOf(patient.getPatientId()), "none", System.currentTimeMillis());
    }
}
