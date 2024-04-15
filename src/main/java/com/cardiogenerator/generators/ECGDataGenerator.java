package com.cardiogenerator.generators;

import java.util.Random;

import com.cardiogenerator.outputs.OutputStrategy;

public class ECGDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private double[] lastEcgValues;
    private static final double PI = Math.PI;

    public ECGDataGenerator(int patientCount) {
        lastEcgValues = new double[patientCount + 1];
        // Initialize the last ECG value for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastEcgValues[i] = 0; // Initial ECG value can be set to 0
        }
    }

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
