package com.cardio_generator.outputs;

import com.data_management.DataStorage;

/**
 * Defines a strategy for outputting patient data generated during simulations.
 * This interface is intended to be implemented by various output handlers that may direct data to different
 * destinations such as the console, files, web sockets, or other network streams.
 */
public interface OutputStrategy {
    /**
     * Outputs the generated data for a patient at a specific timestamp.
     * Implementations of this method should handle how data is formatted and transmitted based on the specific output
     * medium being used (e.g., console, file, network).
     *
     * @param patientId The ID of the patient for whom data is being output.
     * @param timestamp The time at which the data was generated, expressed as milliseconds from the epoch.
     * @param label A descriptive label for the type of data being output, such as "ECG" or "Blood Pressure".
     * @param data The actual data generated for the patient.
     */
    void output(int patientId, long timestamp, String label, String data);
}
