package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * Generates blood saturation data for patients in a health monitoring system.
 * This class simulates blood saturation levels, ensuring they remain within a realistic
 * and healthy range. The generated data is output via an implementation of the OutputStrategy interface.
 *
 * Usage: Instantiate with the number of patients, then call generate() to produce and send
 * saturation data for a specific patient.
 *
 * @author John Doe
 */

public class BloodSaturationDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private int[] lastSaturationValues;
    /**
     * Constructs a BloodSaturationDataGenerator for a specified number of patients.
     * Initializes last known saturation values for each patient between 95% and 100%.
     *
     * @param patientCount the total number of patients to manage saturation data for
     */

    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }
    /**
     * Generates and outputs a new blood saturation value for a specified patient.
     * The method simulates a small fluctuation in saturation levels and ensures the value
     * remains within a healthy range of 90% to 100% before outputting it.
     *
     * @param patientId the identifier of the patient for whom to generate data
     * @param outputStrategy the output mechanism for sending the generated data
     */

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate blood saturation values
            int variation = random.nextInt(3) - 1; // -1, 0, or 1 to simulate small fluctuations
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Ensure the saturation stays within a realistic and healthy range
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    Double.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
