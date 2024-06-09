package com.alerts;

import com.alerts.Decorator.PriorityAlertDecorator;
import com.alerts.Decorator.RepeatedAlertDecorator;
import com.alerts.Factory.*;
import com.alerts.Strategy.*;
import com.data_management.DataStorage;
import com.data_management.Patient;

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

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met based on various strategies. Each strategy is responsible for
     * checking a specific health metric. This function initializes and manages
     * the strategies internally.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        // Standard alerts
        checkAndTriggerAlert(patient, new BloodPressureStrategy("Systolic"), new BloodPressureAlertFactory());
        // checkAndTriggerAlert(patient, new BloodPressureStrategy("Diastolic"), new BloodPressureAlertFactory());
        checkAndTriggerAlert(patient, new OxygenSaturationStrategy(), new BloodOxygenAlertFactory());

        // Decorated alerts with Priority
        Alert ecgAlert = new ECGAlertFactory().createAlert(String.valueOf(patient.getPatientId()), "ECG", System.currentTimeMillis());
        Alert priorityECGAlert = new PriorityAlertDecorator(ecgAlert); // Adding priority to ECG alerts
        checkAndTriggerAlertWithDecorator(patient, new ECGStrategy(), priorityECGAlert);

        // Decorated alerts with Repeated checks
        Alert manuallyTriggeredAlert = new ManuallyTriggeredAlertFactory().createAlert(String.valueOf(patient.getPatientId()), "Manual", System.currentTimeMillis());
        Alert repeatedManuallyTriggeredAlert = new RepeatedAlertDecorator(manuallyTriggeredAlert, 30000); // Repeats every 30 seconds
        checkAndTriggerAlertWithDecorator(patient, new ManuallyTriggeredAlertStrategy(), repeatedManuallyTriggeredAlert);

        // Combined alerts possibly needing both decorations
        Alert combinedAlert = new CombinedAlertFactory().createAlert(String.valueOf(patient.getPatientId()), "Combined", System.currentTimeMillis());
        Alert priorityCombinedAlert = new PriorityAlertDecorator(combinedAlert); // High priority
        Alert repeatedPriorityCombinedAlert = new RepeatedAlertDecorator(priorityCombinedAlert, 60000); // Repeated and high priority
        checkAndTriggerAlertWithDecorator(patient, new CombinedAlertStrategy(), repeatedPriorityCombinedAlert);
    }

    // Method to handle alerts with decorators
    private void checkAndTriggerAlertWithDecorator(Patient patient, AlertStrategy strategy, Alert decoratedAlert) {
        if (strategy.checkAlert(patient)) {
            decoratedAlert.triggerAlert();
        }
    }

    /**
     * Helper method to check a specific strategy and trigger an alert if necessary.
     *
     * @param patient the patient data
     * @param strategy the strategy to use for checking conditions
     */
    private void checkAndTriggerAlert(Patient patient, AlertStrategy strategy, AlertFactory alertFactory) {
        if (strategy.checkAlert(patient)) {
            long timestamp = System.currentTimeMillis(); // or derive from patient data
            Alert alert = alertFactory.createAlert(String.valueOf(patient.getPatientId()),
                    strategy.getClass().getSimpleName().replace("Strategy", ""), timestamp);
            triggerAlert(alert);
        }
    }

    /**
     * Triggers an alert for the monitoring system.
     * This method can be extended to notify medical staff, log the alert, or perform other actions.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        System.out.println("Alert was triggered. Details:");
        System.out.println("Condition: " + alert.getCondition());
        System.out.println("Patient ID: " + alert.getPatientId());
        System.out.println("Timestamp: " + alert.getTimestamp());
    }
}

