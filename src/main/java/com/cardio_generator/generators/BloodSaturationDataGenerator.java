package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * implements the PatientDataGenerator interface to generate blood saturation data for simulated patients
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {//Class names are written in UpperCamelCase.

    /** the random number generator to generate the last saturation */
    private static final Random random = new Random();

    /** an array to store the last saturation values for each patient */
    private int[] lastSaturationValues;

    /**
     * constructs a new BloodSaturationDataGenerator with the specified number of patients
     *
     * @param patientCount the number of patients that will get saturation data generated
     */
    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }

    /**
     * generates blood saturation data for the specified patient and outputs it using a certain output strategy
     *
     * @param patientId the ID of the patient
     * @param outputStrategy the output strategy used to output the generated data
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {

        /**
         * @throws exception if the outputstrategy or the calculation of the saturaion value fails to be executed correctly
         */
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