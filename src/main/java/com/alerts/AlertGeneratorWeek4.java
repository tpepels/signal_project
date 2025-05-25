package com.alerts;

import com.data_management.Patient;
import java.util.List;

/**
 * AlertGeneratorNEW is a singleton class responsible for evaluating a single patients data
 * using a list of alert strategies. Each strategy represents a health metric or condition
 * that should be checked for potential alerts.
 */
public class AlertGeneratorWeek4 {

    private static AlertGeneratorWeek4 instance;

    private final AlertFactory bloodPressureFactory = new BloodPressureAlertFactory();
    private final AlertFactory bloodOxygenFactory = new BloodOxygenAlertFactory();
    private final AlertFactory ecgFactory = new ECGAlertFactory();

 
    private AlertGeneratorWeek4() {
    
    }


    public static synchronized AlertGeneratorWeek4 getInstance() {
        if (instance == null) {
            instance = new AlertGeneratorWeek4();
        }
        return instance;
    }

    /**
     * Evaluates a single patient using a list of alert strategies.
     * Each strategy is responsible for detecting a specific type of alert.
     * If an alert is triggered, it is decorated using the appropriate factory
     * and then passed to the trigger mechanism.
     *
     * @param patient the patient whose data will be evaluated
     * @param strategies a list of alert strategies to apply
     */
    public void evaluatePatient(Patient patient, List<AlertStrategy> strategies) {
        for (AlertStrategy strategy : strategies) {
            Alert rawAlert = strategy.checkAlert(patient);
            if (!"none".equals(rawAlert.getCondition())) {
                Alert decoratedAlert = decorateAlert(strategy, rawAlert);
                triggerAlert(decoratedAlert);
            }
        }
    }

    /**
     * Uses the appropriate alert factory to create a more specific decorated alert,
     * based on the type of strategy that triggered it.
     *
     * @param strategy the strategy that triggered the alert
     * @param baseAlert the original alert returned by the strategy
     * @return a decorated alert with strategy-specific customization
     */
    private Alert decorateAlert(AlertStrategy strategy, Alert baseAlert) {
        if (strategy instanceof BloodPressureStrategy) {
            return bloodPressureFactory.createAlert(baseAlert.getPatientId(), baseAlert.getCondition(), baseAlert.getTimestamp());
        } else if (strategy instanceof OxygenSaturationStrategy) {
            return bloodOxygenFactory.createAlert(baseAlert.getPatientId(), baseAlert.getCondition(), baseAlert.getTimestamp());
        } else if (strategy instanceof HeartRateStrategy || strategy instanceof ECGAlertFactory) {
            return ecgFactory.createAlert(baseAlert.getPatientId(), baseAlert.getCondition(), baseAlert.getTimestamp());
        }
        return baseAlert; 
    }

    /**
     * Handles the final alert action such as logging or notifying medical staff.
     * Currently implemented as a console print for demonstration purposes.
     *
     * @param alert the alert that was triggered and decorated
     */
    private void triggerAlert(Alert alert) {
        System.out.println("[ALERT] Patient ID: " + alert.getPatientId()
                + " | Condition: " + alert.getCondition()
                + " | Timestamp: " + alert.getTimestamp());
    }
}
