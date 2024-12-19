package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        // Implementation goes here
        List<PatientRecord> patientRecords = patient.getRecords(1700000000000L, 1800000000000L);
        evaluateBloodPressure(patient, patientRecords);
        evaluateOxygenSaturation(patient, patientRecords);
        evaluateHypotensiveHypoxemia(patient, patientRecords);
        evaluateECG(patient, patientRecords);

    }

    private void evaluateBloodPressure(Patient patient, List<PatientRecord> records) {
        double previousSystolic = -1;
        int trendCount = 0;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("SystolicPressure")) {
                double currentSystolic = record.getMeasurementValue();

                // Check for trend alert
                if (previousSystolic != -1) {
                    if (currentSystolic - previousSystolic > 10) {
                        trendCount++;
                    } else {
                        trendCount = 0;
                    }

                    if (trendCount >= 2) {
                        triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Blood Pressure Trend Alert", record.getTimestamp()));
                    }
                }
                previousSystolic = currentSystolic;

                // Check for critical thresholds
                if (currentSystolic > 180 || currentSystolic < 90) {
                    triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Critical Blood Pressure Alert", record.getTimestamp()));
                }
            }
        }
    }

    private void evaluateOxygenSaturation(Patient patient, List<PatientRecord> records) {
        double previousSaturation = -1;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("OxygenSaturation")) {
                double currentSaturation = record.getMeasurementValue();

                // Check for low saturation alert
                if (currentSaturation < 92) {
                    triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Low Oxygen Saturation Alert", record.getTimestamp()));
                }

                // Check for rapid drop alert
                if (previousSaturation != -1 && (previousSaturation - currentSaturation >= 5)) {
                    triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Rapid Drop in Oxygen Saturation Alert", record.getTimestamp()));
                }
                previousSaturation = currentSaturation;
            }
        }
    }

    private void evaluateHypotensiveHypoxemia(Patient patient, List<PatientRecord> records) {
        boolean lowBloodPressure = false;
        boolean lowOxygenSaturation = false;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("SystolicPressure") && record.getMeasurementValue() < 90) {
                lowBloodPressure = true;
            }
            if (record.getRecordType().equals("OxygenSaturation") && record.getMeasurementValue() < 92) {
                lowOxygenSaturation = true;
            }
            if (lowBloodPressure && lowOxygenSaturation) {
                triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Hypotensive Hypoxemia Alert", record.getTimestamp()));
                break;
            }
        }
    }

    private void evaluateECG(Patient patient, List<PatientRecord> records) {
        // Placeholder for ECG evaluation logic
        double previousPeak = -1;
        double movingAverage = 0;
        int windowSize = 5;
        int peakCount = 0;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("ECG")) {
                double currentPeak = record.getMeasurementValue();

                movingAverage = updateMovingAverage(movingAverage, previousPeak, currentPeak, windowSize);

                if (previousPeak != -1 && currentPeak > movingAverage * 1.5) {
                    triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Abnormal ECG Peak Alert", record.getTimestamp()));
                    peakCount++;
                }

                if (peakCount >= 3) {
                    triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Multiple Abnormal ECG Peaks Alert", record.getTimestamp()));
                    break;
                }

                previousPeak = currentPeak;
            }
        }
    }

    private double updateMovingAverage(double currentAverage, double previousValue, double newValue, int windowSize) {
        if (previousValue == -1) {
            return newValue;
        }

        // Adjust moving average with sliding window
        return currentAverage + (newValue - previousValue) / windowSize;
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
        System.out.println("Alert triggered: " + alert.getCondition() + " for Patient ID: " + alert.getPatientId() +
                " at Timestamp: " + alert.getTimestamp());

    }
}
