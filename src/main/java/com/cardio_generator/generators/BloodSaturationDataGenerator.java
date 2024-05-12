package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The BloodSaturationDataGenerator class generates blood saturation data for patients.
 * It implements the PatientDataGenerator interface.
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {
    
    /** The random number generator used for generating blood saturation data. */
    private static final Random random = new Random();

    /** An array to store the last recorded blood saturation values for each patient. */
    private int[] lastSaturationValues;

    /**
     * Constructs a BloodSaturationDataGenerator object with the specified number of patients.
     * Initializes an array to store the last recorded blood saturation values for each patient.
     * 
     * @param patientCount The number of patients for whom blood saturation data will be generated.
     */
    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }

    /**
     * Generates blood saturation data for the specified patient and outputs it using the given output strategy.
     * 
     * @param patientId      The ID of the patient for whom blood saturation data is generated.
     * @param outputStrategy The output strategy used to output the generated blood saturation data.
     * @throws IllegalArgumentException If the patient ID is invalid.
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
                    Integer.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
