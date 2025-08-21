package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    //Field names should be lowerCamelCase.
    //Renamed from AlertStates to alertStates.
    //Added a clarifying comment.
    /**
     * Tracks alert state for each patient: false = resolved, true = triggered.
     */
    private final boolean[] alertStates;

    /**
     * Constructs an AlertGenerator for the given number of patients.
     * @param patientCount the number of patients to track
     */
    public AlertGenerator(int patientCount) {
        //Use 'this.' for field assignment to avoid ambiguity.
        this.alertStates = new boolean[patientCount + 1];
    }
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                //90% chance to resolve the alert.
                if (randomGenerator.nextDouble() < 0.9) {
                    alertStates[patientId] = false;
                    // Output the resolved alert.
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                //Local variable names should be camelCase.
                double lambda = 0.1; //Average rate (alerts per period), adjust based on desired frequency.
                double p = -Math.expm1(-lambda); //Probability of at least one alert in the period.
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    //Output the triggered alert.
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            //Error messages should be clear and concise.
            System.err.println("Error generating alert data for patient " + patientId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
