package com.cardio_generator.outputs;

/**
 * the OutputStrategy interface defines the condition for output strategies in the Cardio Generator system
 * these strategies are responsible for processing and outputting the generated data
 */
public interface OutputStrategy {

    /**
     * outputs the generated data for a specific patient
     *
     * @param patientId the id of the patient
     * @param timestamp the timestamp contains the time at which it was generated
     * @param label the label associated with the data
     * @param data the data to be output
     */
    void output(int patientId, long timestamp, String label, String data);
}