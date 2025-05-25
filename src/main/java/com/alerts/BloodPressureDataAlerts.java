package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The {@code BloodPressureDataAlerts} class is responsible for monitoring and evaluating
 * blood pressure readings (systolic and diastolic) for each patient.
 * 
 * This implementation follows the {@link AlertTester} interface and returns an {@link Alert}
 * object if any of the following conditions are detected:
 * 
 * 
 *     Critical Threshold Breach: Systolic exceeds 180 or drops below 90 mmHg;
 *         Diastolic exceeds 120 or drops below 60 mmHg.
 *     Trend Alert: Consistent increase or decrease in three consecutive readings
 *         with changes greater than 10 mmHg.
 * 
 * 
 * Assumptions:
 * 
 *     
 *     Only the first condition detected is returned per call — others are ignored.
 *     The method processes both systolic and diastolic readings independently.
 *     Readings are timestamped and sorted before trend analysis.
 * 
 */
public class BloodPressureDataAlerts implements AlertTester {

    /**
     * Main entry point to check a patient's blood pressure data against all alert conditions.
     *
     * @param patient the patient whose data is being evaluated
     * @return an {@link Alert} object if any condition is met; otherwise, an alert with condition \"none\"
     */
    @Override
    public Alert check(Patient patient) {
        List<PatientRecord> allRecords = patient.getRecords(0, Long.MAX_VALUE);
        List<PatientRecord> systolic = new ArrayList<>();
        List<PatientRecord> diastolic = new ArrayList<>();

        
        for (PatientRecord record : allRecords) {
            String type = record.getRecordType();
            if (type.equalsIgnoreCase("SystolicBloodPressure")) {
                systolic.add(record);
            } else if (type.equalsIgnoreCase("DiastolicBloodPressure")) {
                diastolic.add(record);
            }
        }

      
        Alert systolicThreshold = checkCriticalThreshold(systolic, 90, 180, "SystolicBloodPressure", patient);
        if (!systolicThreshold.getCondition().equals("none")) return systolicThreshold;

        Alert diastolicThreshold = checkCriticalThreshold(diastolic, 60, 120, "DiastolicBloodPressure", patient);
        if (!diastolicThreshold.getCondition().equals("none")) return diastolicThreshold;

        
        Alert systolicTrend = checkTrend(systolic, "SystolicBloodPressure", patient);
        if (!systolicTrend.getCondition().equals("none")) return systolicTrend;

        Alert diastolicTrend = checkTrend(diastolic, "DiastolicBloodPressure", patient);
        if (!diastolicTrend.getCondition().equals("none")) return diastolicTrend;

        return new Alert(String.valueOf(patient.getPatientId()), "none", System.currentTimeMillis());
    }

    /**
     * Evaluates whether any of the given readings fall outside the safe range.
     *
     * @param records list of relevant patient records
     * @param min minimum allowed value
     * @param max maximum allowed value
     * @param type the label of the measurement type
     * @param patient the patient object for ID retrieval
     * @return an {@link Alert} if out-of-range value is found; otherwise, an alert with \"none\"
     */
    private Alert checkCriticalThreshold(List<PatientRecord> records, double min, double max, String type, Patient patient) {
        for (PatientRecord record : records) {
            double value = record.getMeasurementValue();
            if (value < min || value > max) {
                return new Alert(
                        String.valueOf(patient.getPatientId()),
                        "Critical " + type + " reading: " + value,
                        record.getTimestamp()
                );
            }
        }
        return new Alert("", "none", 0);
    }

    /**
     * Detects if a patient's readings are showing a consistent trend (rising or falling).
     *
     * @param records list of blood pressure readings
     * @param type the label of the measurement type
     * @param patient the patient object
     * @return an {@link Alert} if a trend is detected; otherwise, an alert with \"none\"
     */
    private Alert checkTrend(List<PatientRecord> records, String type, Patient patient) {
        if (records.size() < 3) return new Alert("", "none", 0);

        records.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        for (int i = 2; i < records.size(); i++) {
            double first = records.get(i - 2).getMeasurementValue();
            double second = records.get(i - 1).getMeasurementValue();
            double third = records.get(i).getMeasurementValue();

            boolean increasing = second - first > 10 && third - second > 10;
            boolean decreasing = first - second > 10 && second - third > 10;

            if (increasing) {
                return new Alert(
                        String.valueOf(patient.getPatientId()),
                        "Increasing Trend in " + type,
                        records.get(i).getTimestamp()
                );
            }

            if (decreasing) {
                return new Alert(
                        String.valueOf(patient.getPatientId()),
                        "Decreasing Trend in " + type,
                        records.get(i).getTimestamp()
                );
            }
        }

        return new Alert("", "none", 0);
    }
}
