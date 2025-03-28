package com.cardio_generator.outputs;

/**
 * This interface defines the method to output patient data.
 */
public interface OutputStrategy {

    /**
     * Outputs the patient data.
     *
     * @param patientId the patient ID
     * @param timestamp the timestamp of the data
     * @param label the label of the data
     * @param data the data to output
     */
    void output(int patientId, long timestamp, String label, String data);
}
