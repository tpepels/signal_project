package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * {@code BloodSaturationDataGenerator} is an implementation of the {@link PatientDataGenerator} interface
 * that generates simulated blood saturation data for a set of patients. The saturation values are initialized
 * to a baseline value, and the class generates these values in a range (90% to 100%).
 *
 * The {@code generate} method simulates the blood saturation level for a specific patient and ensures
 * that the value stays within the range of 90% to 100%. 
 */

public class BloodSaturationDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private int[] lastSaturationValues;


/**
 * Constructs a {@code BloodSaturationDataGenerator} instance that initializes
 * baseline blood saturation values for each patient.
 *
 * The saturation values are randomly initialized between 95 and 100 for each patient ID.
 *
 * @param patientCount the total number of patients to simulate.
 */

    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }

/**
 * Generates simulated blood saturation data for a given patient and sends the data
 * through the specified {@code OutputStrategy}.
 *
 * The saturation value fluctuates slightly around the patient's previous value and
 * is clamped within a realistic range of 90% to 100%.
 *
 * @param patientId the ID of the patient for whom data is being generated.
 * @param outputStrategy the strategy used to output the generated data.
 *
 * @throws RuntimeException if an unexpected error occurs during data generation or output
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
