package com.cardio_generator.outputs;

/**
 * Defines a strategy for outputting data.
 * Classes implementing this interface can specify their own method for outputting.
 */
public interface OutputStrategy {
    /**
     * Outputs data for a patient.
     * 
     * @param patientId patientId associated to the data.
     * @param timestamp time at which the outputted data was generated.
     * @param label label describing the type of data.
     * @param data the specific data associated with a patientId .
     */
    void output(int patientId, long timestamp, String label, String data);
}
