package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import java.util.List;

/**
 * This class is responsible for evaluating patient health data
 * and generating alerts based on a set of predefined medical conditions.
 *
 * It serves as the main orchestrator that connects {@link Patient} data from the
 * {@link DataStorage} system with modular {@link AlertTester} implementations. Each
 * {@code AlertTester} encapsulates a rule or pattern (e.g., low oxygen, high heart rate)
 * used to evaluate patients.
 *
 * Assumption: Alerts are checked independently and sequentially. If multiple alerts are triggered,
 * each one is immediately printed. 
 */
public class AlertGeneratorWeek3 {
    private final DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a reference to the patient data repository.
     *
     * @param dataStorage the storage system containing all patient data
     */
    public AlertGeneratorWeek3(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates a single patient using a list of alert checkers.
     *
     * Each {@link AlertTester} in the list is invoked to check for a specific alert condition.
     * If an alert is returned with a non-\"none\" condition, {@link #triggerAlert(Alert)} is called.
     *
     * Assumptions:
     * 
     *   Each alert tester operates independently.
     *   A patient can trigger multiple alerts in one evaluation pass.
     *   The returned {@link Alert} from each tester is assumed to be non-null and valid.
     * 
     *
     * @param patient the patient to be evaluated
     * @param alert_testers a list of alert rules to be applied to the patient
     */
    public void evaluateData(Patient patient, List<AlertTester> alert_testers) {
        for (AlertTester tester : alert_testers) {
            Alert alert = tester.check(patient);

            // Skip alerts that don't indicate any issue
            if (!alert.getCondition().equals("none")) {
                triggerAlert(alert);
            }
        }
    }

    /**
     * Handles the triggering logic for alerts.
     *
     * Currently implemented as a console print for simplicity, but can be extended
     * to send notifications, update medical dashboards, or write to logs.
     *
     * @param alert the alert to be triggered
     */
    private void triggerAlert(Alert alert) {
        System.out.println("Notify the nurse, patient with ID: " + alert.getPatientId()
                + " has: " + alert.getCondition());
    }
}

