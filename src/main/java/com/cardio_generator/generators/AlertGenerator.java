package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * a generator for generating alerts for patients
 */
public class AlertGenerator implements PatientDataGenerator {

    /** the random number generator */
    public static final Random RANDOM_GENERATOR = new Random();

    /** the array to store the alert states for each patient */
    private boolean[] alertStates; // false = resolved, true = pressed

    /**
     * constructs an AlertGenerator object.
     *
     * @param patientCount the number of patients
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * generates alerts for a given patient and outputs them using a certain output strategy
     *
     * @param patientId      the ID of the patient
     * @param outputStrategy the output strategy to use for outputting alerts
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                // 90% chance to resolve
                if (RANDOM_GENERATOR.nextDouble() < 0.9) {
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
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
