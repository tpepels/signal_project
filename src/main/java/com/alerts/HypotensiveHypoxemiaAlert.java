package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code HypotensiveHypoxemiaAlert} class implements a combined critical condition check.
 *
 * It evaluates whether a patient is experiencing both:
 * 
 *     Low systolic blood pressure — below 90 mmHg
 *     Low blood oxygen saturation — below 92%
 * 
 * within a close time proximity (=<) 5 minutes apart).
 *
 * Assumptions:
 * 
 *     Only \"SystolicBloodPressure\" and \"BloodOxygenSaturation\" records are relevant.
 *     Readings from both categories are checked pairwise against each other.
 *     Time difference threshold between matching readings is ≤ 300,000 ms (5 minutes).
 *     Only one alert is generated per patient per check cycle (first valid match).
 * 
 */
public class HypotensiveHypoxemiaAlert implements AlertTester {

    /**
     * Evaluates the patient's data to detect hypotensive hypoxemia conditions.
     *
     * @param patient the patient whose data is to be evaluated
     * @return an {@link Alert} if both critical low pressure and oxygen levels occur close in time,
     *         or an alert with {@code "none"} condition if no match is found
     */
    @Override
    public Alert check(Patient patient) {
        List<PatientRecord> allRecords = patient.getRecords(0, Long.MAX_VALUE);

        List<PatientRecord> systolicRecords = new ArrayList<>();
        List<PatientRecord> saturationRecords = new ArrayList<>();

       
        for (PatientRecord record : allRecords) {
            String type = record.getRecordType();
            if (type.equalsIgnoreCase("SystolicBloodPressure")) {
                systolicRecords.add(record);
            } else if (type.equalsIgnoreCase("BloodOxygenSaturation")) {
                saturationRecords.add(record);
            }
        }

        
        for (PatientRecord bp : systolicRecords) {
            if (bp.getMeasurementValue() < 90.0) {
                for (PatientRecord oxy : saturationRecords) {
                    if (oxy.getMeasurementValue() < 92.0) {
                        long timeDiff = Math.abs(bp.getTimestamp() - oxy.getTimestamp());
                        if (timeDiff <= 300_000) { // within 5 minutes
                            return new Alert(
                                    String.valueOf(patient.getPatientId()),
                                    "Hypotensive Hypoxemia Alert: BP=" + bp.getMeasurementValue() +
                                            ", O2=" + oxy.getMeasurementValue(),
                                    Math.max(bp.getTimestamp(), oxy.getTimestamp())
                            );
                        }
                    }
                }
            }
        }

     
        return new Alert(String.valueOf(patient.getPatientId()), "none", System.currentTimeMillis());
    }
}
