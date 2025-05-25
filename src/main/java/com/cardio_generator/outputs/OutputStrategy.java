package com.cardio_generator.outputs;


/**
 * Interface for defining strategies to output patient data. 
 *
 * @param patientId The ID of the patient whose data is being output.
 * @param timestamp The timestamp indicating when the data was recorded.
 * @param label A label for data.
 * @param data The actual data to be output.
 */
public interface OutputStrategy {
    void output(int patientId, long timestamp, String label, String data);
}
