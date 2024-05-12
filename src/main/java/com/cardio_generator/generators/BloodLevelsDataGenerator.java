package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The BloodLevelsDataGenerator class generates blood level data (cholesterol, white blood cells, and red blood cells)
 * for patients.
 * It implements the PatientDataGenerator interface.
 */
public class BloodLevelsDataGenerator implements PatientDataGenerator {
    
    /** The random number generator used for generating blood level data. */
    private static final Random random = new Random();
    
    /** An array to store the baseline cholesterol levels for each patient. */
    private final double[] baselineCholesterol;
    
    /** An array to store the baseline white blood cell counts for each patient. */
    private final double[] baselineWhiteCells;
    
    /** An array to store the baseline red blood cell counts for each patient. */
    private final double[] baselineRedCells;

    /**
     * Constructs a BloodLevelsDataGenerator object with the specified number of patients.
     * Initializes arrays to store baseline values for cholesterol, white blood cells, and red blood cells.
     * 
     * @param patientCount The number of patients for whom blood level data will be generated.
     */
    public BloodLevelsDataGenerator(int patientCount) {
        baselineCholesterol = new double[patientCount + 1];
        baselineWhiteCells = new double[patientCount + 1];
        baselineRedCells = new double[patientCount + 1];

        // Generate baseline values for each patient
        for (int i = 1; i <= patientCount; i++) {
            baselineCholesterol[i] = 150 + random.nextDouble() * 50; // Initial random baseline
            baselineWhiteCells[i] = 4 + random.nextDouble() * 6; // Initial random baseline
            baselineRedCells[i] = 4.5 + random.nextDouble() * 1.5; // Initial random baseline
        }
    }

    /**
     * Generates blood level data (cholesterol, white blood cells, and red blood cells) for the specified patient 
     * and outputs them using the given output strategy.
     * 
     * @param patientId      The ID of the patient for whom blood level data is generated.
     * @param outputStrategy The output strategy used to output the generated blood level data.
     * @throws IllegalArgumentException If the patient ID is invalid.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Generate values around the baseline for realism
            double cholesterol = baselineCholesterol[patientId] + (random.nextDouble() - 0.5) * 10; // Small variation
            double whiteCells = baselineWhiteCells[patientId] + (random.nextDouble() - 0.5) * 1; // Small variation
            double redCells = baselineRedCells[patientId] + (random.nextDouble() - 0.5) * 0.2; // Small variation

            // Output the generated values
            outputStrategy.output(patientId, System.currentTimeMillis(), "Cholesterol", Double.toString(cholesterol));
            outputStrategy.output(patientId, System.currentTimeMillis(), "WhiteBloodCells",
                    Double.toString(whiteCells));
            outputStrategy.output(patientId, System.currentTimeMillis(), "RedBloodCells", Double.toString(redCells));
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood levels data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
