package com.cardiogenerator.generators; // Style: removed underscore from package name

import com.cardiogenerator.outputs.OutputStrategy;

import java.util.Random;

/** Responsible for generating simulated patient alerts. */
public class AlertGeneratorRandom implements PatientDataGenerator {

    /** Random number generator */
    public static final Random randomGenerator = new Random();
    
    /** Tracks which patients have active alerts. */
    private boolean[] alertStates; // false = resolved, true = triggered
    // Style: changed variable name AlertStates to alertStates

    /**
     * Constructs an {@code AlertGenerator} for the specified number of patients.
     * Initializes the alert states to "resolved" for each patient.
     *
     * @param patientCount The number of patients for which alerts will be generated.
     */
    public AlertGeneratorRandom(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Randomly generates and outputs an alert for a specific patient.
     *
     * @param patientId The ID of the patient for whom the alert is generated.
     * @param outputStrategy The strategy used to output the generated alert data.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert resolution
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double lambda = 0.1; // Average rate of alert generation
                //again changed Lambda to lambda for correct convention

                double p = -Math.expm1(-lambda); // Probability of an alert being triggered
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert trigger
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
