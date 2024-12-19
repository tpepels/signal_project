package com.cardio_generator.outputs;

/**
 * The {@code ConsoleOutputStrategy} class implements the {@link OutputStrategy} interface.
 * It outputs patient data to the console.
 */
public class ConsoleOutputStrategy implements OutputStrategy {

    /**
     * Outputs the specified data to the console.
     *
     * @param patientId the unique identifier of the patient
     * @param timestamp the time at which the data was generated, in milliseconds since the Unix epoch
     * @param label     the label describing the type of data
     * @param data      the data value
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        System.out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
    }
}
