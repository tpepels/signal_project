package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The AlertGenerator class generates alerts for patients based on predefined probabilities.
 * It implements the PatientDataGenerator interface.
 */
public class AlertGenerator implements PatientDataGenerator {
    
    /** The random number generator used for alert generation. */
    public static final Random randomGenerator = new Random();

    /** An array to store the alert states for each patient. */
    private boolean[] alertStates;

    /**
     * Constructs an AlertGenerator object with the specified number of patients.
     * 
     * @param patientCount The number of patients for whom alerts will be generated.
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates alerts for the specified patient and outputs them using the given output strategy.
     * 
     * @param patientId      The ID of the patient for whom alerts are generated.
     * @param outputStrategy The output strategy used to output the generated alerts.
     * @throws IllegalArgumentException If the patient ID is invalid.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the resolved alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the triggered alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
