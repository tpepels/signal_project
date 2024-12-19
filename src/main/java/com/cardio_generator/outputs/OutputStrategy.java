package com.cardio_generator.outputs;

public interface OutputStrategy {

    /**
     * Outputs health data for a specific patient.
     *
     * @param patientId The ID of the patient for whom the data is being outputted.
     * @param timestamp The timestamp associated with the data.
     * @param label     The label or type of data being outputted.
     * @param data      The actual data being outputted.
     */
    
    void output(int patientId, long timestamp, String label, String data);
}
