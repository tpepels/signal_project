package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

public class AlertGenerator implements PatientDataGenerator {

    public static final Random RANDOMGENERATOR = new Random();//constants are written in UPPER_SNAKE_CASE
    private boolean[] alertStates; // false = resolved, true = pressed//non-static variables are written in lowerCamelCase

    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOMGENERATOR.nextDouble() < 0.9) { // 90% chance to resolve //RANDOMGENERATOR renaming
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency//local variables are lowerCamelCase
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOMGENERATOR.nextDouble() < p;//RANDOMGENERATOR renaming

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
