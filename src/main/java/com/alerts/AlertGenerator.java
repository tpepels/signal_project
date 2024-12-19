package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.strategies.AlertStrategy;
import com.strategies.BloodPressureStrategy;
import com.strategies.ECGStrategy;
import com.strategies.OxygenSaturationStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private static DataStorage dataStorage;
    private static List<Alert> alerts;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}
     * and a list to store generated alerts.
     *
     * @param dataStorage the data storage system that provides access to patient data
     * @param alerts      the list to store generated alerts
     * @throws IllegalArgumentException if {@code dataStorage} is {@code null}
     */
    public AlertGenerator(DataStorage dataStorage, List<Alert> alerts) {
        if (dataStorage == null) {
            throw new IllegalArgumentException("DataStorage cannot be null");
        }
        AlertGenerator.dataStorage = dataStorage;
        AlertGenerator.alerts = alerts;
    }

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * Initializes the alerts list as an empty list.
     *
     * @param dataStorage the data storage system that provides access to patient data
     * @throws IllegalArgumentException if {@code dataStorage} is {@code null}
     */
    public AlertGenerator(DataStorage dataStorage) {
        if (dataStorage == null) {
            throw new IllegalArgumentException("DataStorage cannot be null");
        }
        AlertGenerator.dataStorage = dataStorage;
        AlertGenerator.alerts = new ArrayList<>();
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is added to the alerts list.
     *
     * @param patient the patient data to evaluate for alert conditions
     * @return a list of generated alerts
     */
    public static List<Alert> evaluateData(Patient patient) {
        List<PatientRecord> records = dataStorage.getRecords(patient.getPatientId(), 0, System.currentTimeMillis());
        checkBloodPressure(patient, records, new BloodPressureStrategy());
        checkBloodSaturationAlerts(patient, records, new OxygenSaturationStrategy());
        checkHypotensiveHypoxemiaAlerts(patient, records, new BloodPressureStrategy(), new OxygenSaturationStrategy());
        checkECGDataAlerts(patient, records, new ECGStrategy());
        return alerts;
    }

    /**
     * Checks the ECG data for alerts related to abnormal heart rates and irregular beat patterns.
     * Abnormal heart rates are defined as below 50 or above 100 beats per minute. Irregular beat patterns
     * are identified based on the time intervals between successive heartbeats, with a tolerance of 200 milliseconds
     * from the expected interval calculated based on the average beats per minute.
     *
     * @param patient   The patient whose ECG data is being checked.
     * @param records   A list of patient records containing heart rate data and associated timestamps.
     * @param strategy  The strategy to use for evaluating ECG data.
     */
    private static void checkECGDataAlerts(Patient patient, List<PatientRecord> records, ECGStrategy strategy) {
        AlertFactory factory = new ECGAlertFactory();

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("HeartRate") && strategy.checkAlert(record)) {
                Alert alert = factory.createAlert(patient.getPatientId(), "AbnormalHeartRate", record.getTimestamp());
                alert = new RepeatedAlertDecorator(alert, 3);
                triggerAlert(alert);
            }
        }

        if (strategy.checkIrregularBeat(records)) {
            Alert alert = factory.createAlert(patient.getPatientId(), "IrregularHeartBeat", System.currentTimeMillis());
            alert = new RepeatedAlertDecorator(alert, 3);
            triggerAlert(alert);
        }
    }

    /**
     * Checks for concurrent hypotension and hypoxemia within the patient's records.
     * Hypotension is identified by a systolic blood pressure below 90 mmHg. Hypoxemia is defined as
     * a blood saturation level below 92%. An alert is triggered if both conditions are found in the
     * same set of records.
     *
     * @param patient    The patient whose records are being evaluated.
     * @param records    A list of patient records that includes blood pressure and blood saturation measurements.
     * @param bpStrategy The strategy to use for evaluating blood pressure data.
     * @param satStrategy The strategy to use for evaluating blood saturation data.
     */
    private static void checkHypotensiveHypoxemiaAlerts(Patient patient, List<PatientRecord> records, BloodPressureStrategy bpStrategy, OxygenSaturationStrategy satStrategy) {
        AlertFactory factory = new BloodPressureAlertFactory();

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("BloodPressure") && bpStrategy.checkAlert(record)) {
                double systolic = record.getMeasurementValues()[0];
                for (PatientRecord saturationRecord : records) {
                    if (saturationRecord.getRecordType().equals("BloodSaturation") && satStrategy.checkAlert(saturationRecord)) {
                        double saturation = saturationRecord.getMeasurementValue();
                        if (systolic < 90 && saturation < 92) {
                            Alert alert = factory.createAlert(record.getPatientId(), "HypotensiveHypoxemiaAlert", record.getTimestamp());
                            alert = new PriorityAlertDecorator(alert, 1);  // Example of using decorator
                            triggerAlert(alert);
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks for alerts related to blood saturation levels from patient records. It generates alerts for
     * low blood saturation when the level falls below 92%. Additionally, it checks for rapid drops in saturation,
     * triggering an alert if the saturation decreases by 5% or more within a 10-minute window.
     *
     * @param patient   The patient whose blood saturation is being monitored.
     * @param records   A list of patient records that include blood saturation measurements.
     * @param strategy  The strategy to use for evaluating blood saturation data.
     */
    private static void checkBloodSaturationAlerts(Patient patient, List<PatientRecord> records, AlertStrategy strategy) {
        AlertFactory factory = new BloodOxygenAlertFactory();
        OxygenSaturationStrategy oxygenStrategy = (OxygenSaturationStrategy) strategy;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("BloodSaturation")) {
                boolean alertTriggered = strategy.checkAlert(record);

                if (alertTriggered) {
                    if (oxygenStrategy.isLowSaturationAlert(record)) {
                        Alert alert = factory.createAlert(record.getPatientId(), "LowBloodSaturation", record.getTimestamp());
                        alert = new PriorityAlertDecorator(alert, 2);  // Example of using decorator
                        triggerAlert(alert);
                    }

                    if (oxygenStrategy.isRapidDropAlert(record)) {
                        Alert alert = factory.createAlert(record.getPatientId(), "RapidBloodSaturationDrop", record.getTimestamp());
                        alert = new PriorityAlertDecorator(alert, 2);  // Example of using decorator
                        triggerAlert(alert);
                    }
                }
            }
        }
    }

    /**
     * Monitors blood pressure records for significant trends and critical threshold breaches.
     * It tracks consecutive increases or decreases in systolic and diastolic pressure, triggering alerts if there
     * are three successive measurements differing by more than 10 mmHg in either direction.
     * Additionally, it checks for critical blood pressure values and triggers alerts when systolic
     * pressure exceeds 180 mmHg, drops below 90 mmHg, or when diastolic pressure exceeds 120 mmHg or
     * falls below 60 mmHg.
     *
     * @param patient   The patient whose blood pressure is being monitored.
     * @param records   A list of patient records containing blood pressure measurements.
     * @param strategy  The strategy to use for evaluating blood pressure data.
     */
    private static void checkBloodPressure(Patient patient, List<PatientRecord> records, AlertStrategy strategy) {
        AlertFactory factory = new BloodPressureAlertFactory();
        BloodPressureStrategy bpStrategy = (BloodPressureStrategy) strategy;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("BloodPressure")) {
                boolean alertTriggered = strategy.checkAlert(record);

                if (alertTriggered) {
                    Alert alert = null;

                    if (bpStrategy.checkCriticalThresholdAlert(record)) {
                        alert = factory.createAlert(record.getPatientId(), "CriticalBloodPressureThreshold", record.getTimestamp());
                    } else if (bpStrategy.checkIncreasingSystolicTrend()) {
                        alert = factory.createAlert(record.getPatientId(), "IncreasingSystolicBloodPressureTrend", record.getTimestamp());
                    } else if (bpStrategy.checkDecreasingSystolicTrend()) {
                        alert = factory.createAlert(record.getPatientId(), "DecreasingSystolicBloodPressureTrend", record.getTimestamp());
                    } else if (bpStrategy.checkIncreasingDiastolicTrend()) {
                        alert = factory.createAlert(record.getPatientId(), "IncreasingDiastolicBloodPressureTrend", record.getTimestamp());
                    } else if (bpStrategy.checkDecreasingDiastolicTrend()) {
                        alert = factory.createAlert(record.getPatientId(), "DecreasingDiastolicBloodPressureTrend", record.getTimestamp());
                    }

                    if (alert != null) {
                        alert = new RepeatedAlertDecorator(alert, 5);  // Example of using decorator
                        triggerAlert(alert);
                    }
                }
            }
        }
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    protected static void triggerAlert(Alert alert) {
        alerts.add(alert);
        System.out.println("Alert triggered: " + alert.toString());
    }

    /**
     * Retrieves the list of generated alerts.
     *
     * @return the list of generated alerts
     */
    public List<Alert> getAlerts() {
        return alerts;
    }
}
