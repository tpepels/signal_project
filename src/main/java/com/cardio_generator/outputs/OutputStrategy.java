package com.cardio_generator.outputs;

/**
 * The interface for defining output strategies for patient data.
 * 
 * <p>This interface defines a method for outputting patient data, including vital signs and other health-related information.
 * Implementing classes must provide the logic for outputting data according to the specified patient ID, timestamp, label, and data.
 * 
 */
public interface OutputStrategy {
    
    /**
     * Outputs patient data according to the specified parameters.
     * 
     * <p>This method outputs the provided patient data, including the patient ID, timestamp, label, and data.
     * Implementing classes should define the logic for outputting data according to the specified parameters.
     * 
     * @param patientId The ID of the patient associated with the data.
     * @param timestamp The timestamp indicating when the data was generated.
     * @param label The label indicating the type or category of the data.
     * @param data The actual data to be outputted.
     */
    void output(int patientId, long timestamp, String label, String data);
}
