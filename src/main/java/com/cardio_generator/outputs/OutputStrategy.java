package com.cardio_generator.outputs;

/**
 * The OutputStrategy interface represents a strategy for outputting patient data.
 * Implementing classes must define a method to output data for a specific patient.
 */
public interface OutputStrategy {
    
    /**
     * Outputs the specified data for the patient using the given parameters.
     * 
     * @param patientId The ID of the patient.
     * @param timestamp The timestamp of the data.
     * @param label     The label or type of the data.
     * @param data      The actual data to be outputted.
     */
    void output(int patientId, long timestamp, String label, String data);
}
