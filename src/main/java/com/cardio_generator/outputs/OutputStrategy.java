package com.cardio_generator.outputs;
/**
 * Defines the contract for outputting patient data in the cardio generator system.
 * Implementations of this interface are responsible for handling the output of generated
 * patient data, such as writing to the console, files, or external systems.
 * Usage: Implement this interface to define custom output strategies for patient data.
 * The {@code output} method will be called with patient data to be processed or stored.
 */
public interface OutputStrategy {
    /**
     * Outputs patient data using the implemented strategy.
     * This method is called whenever new patient data is generated and needs to be output.
     * Implementations should handle the data appropriately, such as writing to a file,
     * displaying on a console, or sending to a remote server.
     * @param patientId the unique identifier of the patient whose data is being output;
     *                  must be a positive integer corresponding to a valid patient in the system
     * @param timestamp the time at which the data was generated, in milliseconds since the epoch
     * @param label a string label describing the type of data (e.g., "SystolicPressure", "ECG")
     * @param data the actual data value to be output, represented as a string
     * @throws IllegalArgumentException if any parameter is invalid (e.g., negative patientId, null label or data)
     * @throws RuntimeException if an error occurs during the output process
     */
    void output(int patientId, long timestamp, String label, String data);
}
