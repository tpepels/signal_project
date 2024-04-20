package com.cardio_generator.generators;

import java.util.Random; //the imports (non static) are supposed to be in the same block and not separated by a blank line
import com.cardio_generator.outputs.OutputStrategy;

/**
 * Generates and output alert data for patients
 * 
 * The class implements the interface PatiantDataGenerator
 */
public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    private boolean[] AlertStates; // false = resolved, true = pressed

    /**
     * Constructs an AlertGenerator that passed an array of boolean 
     * 
     * @param patientCount number of patients
     */
    public AlertGenerator(int patientCount) {
        AlertStates = new boolean[patientCount + 1];
    }

    /** 
     * Generates alert data by simulating an alert of a patient, based on predefined probabilities, that is either resolved or triggered
     * 
     * @param patientId identifier (integer) of the patient we are generating the alert data for
     * @param outputStrategy startegy to output the alert
     * @throws Exception if illegal argument 
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (AlertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    AlertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double Lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-Lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    AlertStates[patientId] = true;
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
