package com.cardio_generator.outputs;

/**
 * interface that declares a method to output data
 */
public interface OutputStrategy {

    /**
     * Outputs data
     * 
     * @param patientId identifier (integer) of the patient
     * @param timestamp timestamp of the data
     * @param label     label of the data
     * @param data      data associated with the patient
     */
    void output(int patientId, long timestamp, String label, String data);
}
