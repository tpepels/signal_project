package com.cardio_generator.outputs;
/**
<<<<<<< HEAD
 * Interface for defining the output strategy for generated patient data.
 * This interface allows different output mechanisms (e.g., console, file, TCP socket, WebSocket)
 * to be implemented and used interchangeably to handle the generated data.
 * 
 * <p>Implementations of this interface must define how to handle the patient data
 * (such as writing it to a file, sending it over the network, etc.) based on the specified parameters.
 *
 * @see com.cardio_generator.generators.PatientDataGenerator
 */
public interface OutputStrategy {
     /**
     * Outputs the generated data for a patient.
     *
     * @param patientId The unique identifier of the patient.
     * @param timestamp The timestamp when the data was generated.
     * @param label     The label that describes the type of data (e.g., "Alert", "ECG").
     * @param data      The actual data generated for the patient.
=======
 * Interface for defining output strategies for patient data.
 * 
 * This interface provides the contract for outputting generated patient data
 * in different formats or systems, such as console, files, or network.
 */
public interface OutputStrategy {
    /**
     * Outputs the generated patient data.
     *
     * @param patientId The unique identifier for the patient.
     * @param timestamp The timestamp when the data was generated.
     * @param label A label or type describing the data (e.g., heart rate, blood pressure).
     * @param data The actual generated data for the patient.
>>>>>>> a3384e7b374c1c493cd3dd27b62bce7aa8160124
     */
    void output(int patientId, long timestamp, String label, String data);
}
