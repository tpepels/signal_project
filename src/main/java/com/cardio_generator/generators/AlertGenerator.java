package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    private boolean[] alertStates; // Changed variable name "AlertStates" to follow camelCase convention

    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1]; // Changed variable name "AlertStates" to follow camelCase convention
    }

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) { // CChanged variable name "AlertStates" to follow camelCase convention
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false; // Changed variable name "AlertStates" to follow camelCase convention
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true; // Changed variable name "AlertStates" to follow camelCase convention
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
