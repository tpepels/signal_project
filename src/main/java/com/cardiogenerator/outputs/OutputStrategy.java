package com.cardiogenerator.outputs;

/**
 * Define the output method for the OutputStrategy interface
 */
public interface OutputStrategy {
    /**
     * @param patientId - the ID of the patient
     * @param timestamp - the timestamp of the data
     * @param label - the label of the data
     * @param data - the data to output
     */
    void output(int patientId, long timestamp, String label, String data);
}
