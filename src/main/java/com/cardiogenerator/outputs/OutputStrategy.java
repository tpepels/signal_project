package com.cardiogenerator.outputs;

/** Represents a process for outputting health data */
public interface OutputStrategy {

    /**
     * Outputs the data of a measurement.
     *
     * @param patientId The ID of the patient whose data is being output.
     * @param timestamp The timestamp of when the measurement was taken.
     * @param label A label categorizing the type of data.
     * @param data The actual data to be output.
     */
    void output(int patientId, long timestamp, String label, String data);
}
