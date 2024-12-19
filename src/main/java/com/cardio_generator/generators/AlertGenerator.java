package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * Generates alert data for patients. Alerts can either be triggered or resolved based on
 * predefined probabilities. This generator simulates the process of monitoring patients and
 * generating alert signals that might indicate a change in their condition.
 */
public class AlertGenerator implements PatientDataGenerator {

    // Constants should be all uppercase with underscore separation
    public static final Random RANDOM_GENERATOR = new Random();
    // Change variable name to lowerCamelCase
    /**
     * Array to hold alert states for each patient.
     * false indicates the alert is resolved, true indicates the alert is active.
     */
    private boolean[] alertStates; // false = resolved, true = pressed
    // Change variable name to lowerCamelCase and change visibility into private

    /**
     * Constructs an AlertGenerator for a specified number of patients.
     *
     * @param patientCount the number of patients to monitor for alerts.
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates alert data for a specific patient and outputs the result through the provided strategy.
     * The method simulates the possibility of triggering a new alert or resolving an existing one.
     *
     * @param patientId       the ID of the patient for whom to generate alert data.
     * @param outputStrategy  the output strategy to handle the output of the alert data.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                //change variable name to lowerCamelCase
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
