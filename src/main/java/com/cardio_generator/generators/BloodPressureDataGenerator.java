package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The BloodPressureDataGenerator class generates blood pressure data (systolic and diastolic pressure)
 * for patients.
 * It implements the PatientDataGenerator interface.
 */
public class BloodPressureDataGenerator implements PatientDataGenerator {
    
    /** The random number generator used for generating blood pressure data. */
    private static final Random random = new Random();

    /** An array to store the last recorded systolic pressure values for each patient. */
    private int[] lastSystolicValues;

    /** An array to store the last recorded diastolic pressure values for each patient. */
    private int[] lastDiastolicValues;

    /**
     * Constructs a BloodPressureDataGenerator object with the specified number of patients.
     * Initializes arrays to store the last recorded systolic and diastolic pressure values for each patient.
     * 
     * @param patientCount The number of patients for whom blood pressure data will be generated.
     */
    public BloodPressureDataGenerator(int patientCount) {
        lastSystolicValues = new int[patientCount + 1];
        lastDiastolicValues = new int[patientCount + 1];

        // Initialize with baseline values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSystolicValues[i] = 110 + random.nextInt(20); // Random baseline between 110 and 130
            lastDiastolicValues[i] = 70 + random.nextInt(15); // Random baseline between 70 and 85
        }
    }

    /**
     * Generates blood pressure data (systolic and diastolic pressure) for the specified patient 
     * and outputs them using the given output strategy.
     * 
     * @param patientId      The ID of the patient for whom blood pressure data is generated.
     * @param outputStrategy The output strategy used to output the generated blood pressure data.
     * @throws IllegalArgumentException If the patient ID is invalid.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            int systolicVariation = random.nextInt(5) - 2; // -2, -1, 0, 1, or 2
            int diastolicVariation = random.nextInt(5) - 2;
            int newSystolicValue = lastSystolicValues[patientId] + systolicVariation;
            int newDiastolicValue = lastDiastolicValues[patientId] + diastolicVariation;
            // Ensure the blood pressure stays within a realistic and safe range
            newSystolicValue = Math.min(Math.max(newSystolicValue, 90), 180);
            newDiastolicValue = Math.min(Math.max(newDiastolicValue, 60), 120);
            lastSystolicValues[patientId] = newSystolicValue;
            lastDiastolicValues[patientId] = newDiastolicValue;

            outputStrategy.output(patientId, System.currentTimeMillis(), "SystolicPressure",
                    Integer.toString(newSystolicValue));
            outputStrategy.output(patientId, System.currentTimeMillis(), "DiastolicPressure",
                    Integer.toString(newDiastolicValue));
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood pressure data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
