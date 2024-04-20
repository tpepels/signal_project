package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * A generator for simulating blood saturation data for patients.
 * 
 * <p>This generator simulates blood saturation values for patients by generating random fluctuations around baseline values.
 * It ensures that the saturation values remain within a realistic and healthy range.
 * 
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {
    
    /** The random number generator used for generating fluctuations. */
    private static final Random random = new Random();
    
    /** An array to store the last saturation values for each patient. */
    private int[] lastSaturationValues;

    /**
     * Constructs a new BloodSaturationDataGenerator with the specified number of patients.
     * 
     * @param patientCount The number of patients for which to generate blood saturation data.
     */
    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }

    /**
     * Generates blood saturation data for the specified patient and outputs it using the provided output strategy.
     * 
     * <p>This method generates blood saturation data for the specified patient ID and outputs it using the provided output strategy.
     * It simulates small fluctuations around the last saturation value for the patient and ensures that the saturation stays within
     * a realistic and healthy range (90% to 100%).
     * 
     * @param patientId The ID of the patient for which to generate blood saturation data.
     * @param outputStrategy The output strategy to use for outputting the generated data.
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
