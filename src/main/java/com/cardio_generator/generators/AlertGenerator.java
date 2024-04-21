package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The AlertGenerator class implements PatientDataGenerator interface.
 * AlertGenerator creates alerts for patients depending on specific situations.
 *
 * @author Siyu Zhu
 */
public class AlertGenerator implements PatientDataGenerator {

    /**
     * The random generator which is used to decides alert triggers.
     */
    //Final variable names should use all capital letters which is RANDOM_GENERATOR
    public static final Random RANDOM_GENERATOR = new Random();

    /**
     * Create an alertStates array to represent the alert states of patients.
     * True represent the alert has been pressed, false represent the alert has been finished.
     */
    //Variable name change to camelCase which is alertStates
    private boolean[] alertStates; // false = resolved, true = pressed

    /**
     * Creates an AlertGenerator for a given number of patients.
     *
     * @param patientCount the counted number of patients.
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Simulates alert generation for patients based on certain conditions.
     * Outputs alert messages using the provided output strategy.
     *
     * @param patientId the ID number of patients.
     * @param outputStrategy the output strategy which used to output the saturation data.
     */
    @Override
    //Variable name change to camelCase which is lambda
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
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
