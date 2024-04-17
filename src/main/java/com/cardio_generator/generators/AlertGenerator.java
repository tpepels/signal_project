package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * This class generates alert data for patients.
 * Alerts can be triggered or resolved based on a probability distribution.
 * Implementation of the PatientDataGenerator interface.
 */
public class AlertGenerator implements PatientDataGenerator {

    public static final Random RANDOM_GENERATOR = new Random(); // Changed to UPPER_SNAKE_CASE has it is a constant.
    private boolean[] alertStates; // False = resolved, true = pressed. // AlertStates as to start with a small letter

    /**
     * Constructs an AlertGenerator with the specified number of patients.
     *
     * @param patientCount the number of patients
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates alert data for the specified patient ID.
     * Alerts can be triggered or resolved based on a probability distribution.
     * Overridden method from the PatientDataGenerator interface.
     *
     * @param patientId the patient ID
     * @param outputStrategy the output strategy to use
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve.
                    alertStates[patientId] = false;
                    // Output the alert.
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                // Average rate (alerts per period), adjusts based on desired frequency.
                double lambda = 0.1;// Removed upper case L in lambda.
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period.
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert.
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
