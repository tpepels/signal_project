package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy;

// Class names should be nouns and written in UpperCamelCase.
public class AlertGenerator implements PatientDataGenerator {

    // Constants should be written in CONSTANT_CASE
    private static final Random RANDOM_GENERATOR = new Random();
    // Use lowerCamelCase for instance variables
    private boolean[] alertStates; // False = resolved, true = pressed

    // Constructor name should match the class name and be in UpperCamelCase.
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert, method names should be in lowerCamelCase
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                // Local variables should be in lowerCamelCase.
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                // Use clear and meaningful expressions for method calls
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert, method names should be in lowerCamelCase
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            // System.err should be used carefully; better logging would be preferable.
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}

