package com.alerts;

import com.data_management.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * OxygenSaturationStrategy is responsible for evaluating blood oxygen saturation
 * data for a patient to identify abnormal conditions.
 *
 * This strategy triggers alerts in the following cases:
 * - A rapid drop of 5% or more in oxygen saturation within a 10-minute interval.
 * - A saturation level that falls below 92%, indicating hypoxemia.
 *
 * Implements the AlertStrategy interface for integration with alert generation systems.
 */
public class OxygenSaturationStrategy implements AlertStrategy {

    /**
     * Analyzes a patient's oxygen saturation data to detect concerning changes.
     * It checks two types of conditions:
     * 1. A rapid drop (at least 5%) in blood oxygen levels within 10 minutes.
     * 2. A sustained low oxygen level below 92%.
     *
     * The method returns the first alerting condition found, or a default alert with
     * condition "none" if no issues are detected.
     *
     * @param patient the patient whose oxygen saturation data will be evaluated
     * @return an Alert indicating the issue found, or a non-alert if all readings are normal
     */
    @Override
    public Alert checkAlert(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);
        List<PatientRecord> oxygen = new ArrayList<>();

        for (PatientRecord record : records) {
            if (record.getRecordType().equalsIgnoreCase("BloodOxygenSaturation")) {
                oxygen.add(record);
            }
        }

        oxygen.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        
        for (int i = 0; i < oxygen.size(); i++) {
            PatientRecord earlier = oxygen.get(i);
            for (int j = i + 1; j < oxygen.size(); j++) {
                PatientRecord later = oxygen.get(j);
                long timeDiff = later.getTimestamp() - earlier.getTimestamp();
                if (timeDiff > 600_000) break; 
                double drop = earlier.getMeasurementValue() - later.getMeasurementValue();
                if (drop >= 5.0) {
                    return new Alert(
                        String.valueOf(patient.getPatientId()),
                        "Rapid Drop in Blood Oxygen Saturation: from " +
                        earlier.getMeasurementValue() + " to " + later.getMeasurementValue(),
                        later.getTimestamp()
                    );
                }
            }
        }

        
        for (PatientRecord record : oxygen) {
            if (record.getMeasurementValue() < 92.0) {
                return new Alert(
                    String.valueOf(patient.getPatientId()),
                    "Low Blood Oxygen Saturation: " + record.getMeasurementValue(),
                    record.getTimestamp()
                );
            }
        }

        return new Alert(String.valueOf(patient.getPatientId()), "none", System.currentTimeMillis());
    }
}
