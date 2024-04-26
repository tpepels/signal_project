package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The ECGDataGenerator class generates electrocardiogram (ECG) data for patients.
 * It implements the PatientDataGenerator interface.
 */
public class ECGDataGenerator implements PatientDataGenerator {
    
    /** The random number generator used for generating ECG data. */
    private static final Random random = new Random();

    /** An array to store the last recorded ECG values for each patient. */
    private double[] lastEcgValues;

    /** The value of Pi for mathematical calculations. */
    private static final double PI = Math.PI;

    /**
     * Constructs an ECGDataGenerator object with the specified number of patients.
     * Initializes an array to store the last recorded ECG values for each patient.
     * 
     * @param patientCount The number of patients for whom ECG data will be generated.
     */
    public ECGDataGenerator(int patientCount) {
        lastEcgValues = new double[patientCount + 1];
        // Initialize the last ECG value for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastEcgValues[i] = 0; // Initial ECG value can be set to 0
        }
    }

    /**
     * Generates ECG data for the specified patient and outputs it using the given output strategy.
     * 
     * @param patientId      The ID of the patient for whom ECG data is generated.
     * @param outputStrategy The output strategy used to output the generated ECG data.
     * @throws IllegalArgumentException If the patient ID is invalid.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        // TODO Check how realistic this data is and make it more realistic if necessary
        try {
            double ecgValue = simulateEcgWaveform(patientId, lastEcgValues[patientId]);
            outputStrategy.output(patientId, System.currentTimeMillis(), "ECG", Double.toString(ecgValue));
            lastEcgValues[patientId] = ecgValue;
        } catch (Exception e) {
            System.err.println("An error occurred while generating ECG data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }

    /**
     * Simulates the ECG waveform for the specified patient.
     * 
     * @param patientId    The ID of the patient for whom ECG waveform is simulated.
     * @param lastEcgValue The last recorded ECG value for the patient.
     * @return The simulated ECG waveform value.
     */
    private double simulateEcgWaveform(int patientId, double lastEcgValue) {
        // Simplified ECG waveform generation based on sinusoids
        double hr = 60.0 + random.nextDouble() * 20.0; // Simulate heart rate variability between 60 and 80 bpm
        double t = System.currentTimeMillis() / 1000.0; // Use system time to simulate continuous time
        double ecgFrequency = hr / 60.0; // Convert heart rate to Hz

        // Simulate different components of the ECG signal
        double pWave = 0.1 * Math.sin(2 * PI * ecgFrequency * t);
        double qrsComplex = 0.5 * Math.sin(2 * PI * 3 * ecgFrequency * t); // QRS is higher frequency
        double tWave = 0.2 * Math.sin(2 * PI * 2 * ecgFrequency * t + PI / 4); // T wave is offset

        return pWave + qrsComplex + tWave + random.nextDouble() * 0.05; // Add small noise
    }
}
