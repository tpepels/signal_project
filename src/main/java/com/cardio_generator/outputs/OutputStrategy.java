package com.cardio_generator.outputs;
/**
 * Represents a strategy for outputting data in a health monitoring system.
 * This interface defines a method for sending out data related to patient monitoring,
 * such as alerts, vital stats, or other information, based on various labels and timestamps.
 *
 * Usage: Implement this interface to define custom output behaviors, such as writing to files,
 * sending over network, or storing in databases, depending on the needs of the application.
 *
 * @author Tepels
 */
public interface OutputStrategy {
    /**
     * Outputs data for a specific patient based on the provided parameters.
     * This method is designed to handle the outputting of data such as health metrics or alerts
     * and should be implemented to accommodate different types of data and output destinations.
     *
     * @param patientId the unique identifier of the patient to whom the data pertains
     * @param timestamp the time at which the data was recorded or generated
     * @param label a label that categorizes or describes the type of data
     * @param data the actual data string to be output, which could represent various forms of health-related information
     */
    void output(int patientId, long timestamp, String label, String data);
}
