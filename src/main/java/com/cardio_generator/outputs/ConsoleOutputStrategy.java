package com.cardio_generator.outputs;

/**
 * Implements interface to output health data 
 * The strategy formats and prints the data to standard output
 */

public class ConsoleOutputStrategy implements OutputStrategy {
    /**
     * The outputs are form patient health data to the console
     * Method prints the patient ID, timestamp, data label, and the curent data 
     *
     * @param patientId unique identifier for the patient
     * @param timestamp timestamp when the data was obtained
     * @param label descriptive label for the type of data 
     * @param data data to be shown, it is a string
     */

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        System.out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
    }
}
