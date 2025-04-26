package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;
/**
<<<<<<< HEAD
 * A concrete implementation of the {@link PatientDataGenerator} interface that simulates blood saturation data
 * for patients in a health monitoring system.
 *
 * <p>This class generates simulated blood saturation levels for each patient within a realistic range (90% to 100%),
 * with slight fluctuations introduced over time to mimic natural variations in blood oxygen saturation.
 * The generated data is then sent to the specified output strategy.
 * 
 * @see PatientDataGenerator
=======
 * Generates simulated blood saturation data for patients.
 * <p>
 * This class simulates blood saturation levels for a given number of patients. The saturation levels fluctuate
 * within a specified range, and the generated data is then sent to the specified output strategy.
 * </p>
>>>>>>> a3384e7b374c1c493cd3dd27b62bce7aa8160124
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private int[] lastSaturationValues;
<<<<<<< HEAD

    /**
     * Constructs a {@link BloodSaturationDataGenerator} with initial saturation values for a specified number of patients.
     * Each patient is initialized with a blood saturation value between 95% and 100%.
     *
     * @param patientCount The number of patients being simulated.
=======
    /**
     * Constructs a BloodSaturationDataGenerator for a specified number of patients.
     * 
     * Initializes baseline blood saturation values for each patient within the range of 95-100%.
     * 
     * @param patientCount The number of patients to generate data for.
>>>>>>> a3384e7b374c1c493cd3dd27b62bce7aa8160124
     */
    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }
<<<<<<< HEAD

    /**
     * Generates simulated blood saturation data for a single patient. The blood saturation value fluctuates slightly
     * within a realistic range (90% to 100%), and the updated value is sent to the specified output strategy.
     *
     * @param patientId      The unique identifier of the patient whose blood saturation data is being generated.
     * @param outputStrategy The output strategy to which the simulated data is sent.
=======
    /**
     * Generates blood saturation data for a specific patient and sends it to the provided output strategy.
     * <p>
     * The generated saturation value fluctuates by a small variation (between -1, 0, or 1) from the last recorded value,
     * ensuring the saturation remains in the healthy range of 90 to 100%.
     * </p>
     * 
     * @param patientId The unique identifier of the patient.
     * @param outputStrategy The strategy used to output the generated data (e.g., console, file, etc.).
>>>>>>> a3384e7b374c1c493cd3dd27b62bce7aa8160124
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
