package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Generates simulated alert data for patients.
 *
 * The generator maintains alert states for each patient, toggling between
 * {@code "triggered"} and {@code "resolved"} based on a random chance.
 * Alert resolution has a 90% probability per cycle, while alert generation follows
 * a Poisson process approximation using a lambda value.
 *
 * @param patientId the ID of the patient for whom alert data is generated.
 * @param outputStrategy the output mechanism used to dispatch alert status updates.
 * @throws RuntimeException if an unexpected error occurs during alert generation.
 */

public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    private boolean[] AlertStates; // false = resolved, true = pressed

    public AlertGenerator(int patientCount) {
        AlertStates = new boolean[patientCount + 1];
    }

/**
 * Generates and outputs alert data for a specific patient.
 *
 * If the patient is currently in an alert state, there is a 90% chance the alert will be resolved.
 * Otherwise, a new alert may be triggered based on a Poisson process approximation using
 * a predefined lambda value. Alerts are either {@code "triggered"} or {@code "resolved"} and are
 * sent to the specified output strategy.
 *
 * @param patientId the unique identifier of the patient for whom the alert data is generated.
 * @param outputStrategy the output strategy used to send the alert data.
 * @throws RuntimeException if an error occurs while generating or sending alert data
 */

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (AlertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    AlertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double Lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-Lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    AlertStates[patientId] = true;
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
