package com.cardio_generator.generators;

//removed spacing between imports
import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy;

/**
 * Represents an alert generator that generates and outputs alerts for patients.
 * Alerts can be triggered randomly and resolved with a certain probability.
 * It utilizes a pseudo-random number generator to simulate real-time patient alerts. 
 */
public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    private boolean[] alertStates; // false = resolved, true = pressed

    /**
     * Initialises an AlertGenerator with the specified number of patients.
     *
     * @param patientCount The number of patients.
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates or resolves alerts for the specified patient and outputs them using the provided OutputStrategy.
     *
     * @param patientId The ID of the patient.
     * @param outputStrategy The strategy used for outputting the alert.
     * @throws Exception if the data generation fails, e.i. using a non-existent patient id.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double Lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-Lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

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
