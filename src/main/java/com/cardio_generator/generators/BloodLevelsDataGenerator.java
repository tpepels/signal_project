package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * A generator for blood levels data. This generator simulates small fluctuations in blood levels
 * values for each patient. The blood levels values are generated randomly, but they are kept within a realistic
 * and healthy range.
 */
public class BloodLevelsDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private final double[] baselineCholesterol;
    private final double[] baselineWhiteCells;
    private final double[] baselineRedCells;

    /**
     * Creates a new blood levels data generator.
     *
     * @param patientCount The number of patients for which to generate data.
     */
    public BloodLevelsDataGenerator(int patientCount) {
        // Initialize arrays to store baseline values for each patient
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
     * Generates a new blood levels value for the specified patient and outputs it using the provided output
     * strategy.
     *
     * @param patientId The ID of the patient for which to generate data.
     * @param outputStrategy The output strategy to use to output the generated data.
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
