package com.cardio_generator.outputs;

/**
 * The ConsoleOutputStrategy class represents an output strategy that prints data to the console.
 * It implements the OutputStrategy interface.
 */
public class ConsoleOutputStrategy implements OutputStrategy {
    
    /**
     * Outputs the specified data for the patient to the console.
     * 
     * @param patientId The ID of the patient.
     * @param timestamp The timestamp of the data.
     * @param label     The label or type of the data.
     * @param data      The actual data to be outputted.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        System.out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
    }
}
