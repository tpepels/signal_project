package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The interface for generating patient data.
 * 
 * <p>This interface defines a method for generating patient data, which includes vital signs and other health-related information.
 * Implementing classes must provide the logic to generate data for a specific patient and output it using the provided output strategy.
 * 
 */
public interface PatientDataGenerator {
    
    /**
     * Generates patient data and outputs it using the specified output strategy.
     * 
     * <p>This method generates data for the specified patient ID and outputs it using the provided output strategy.
     * Implementing classes should define the logic for generating patient-specific data and use the output strategy to output it.
     * 
     * @param patientId The ID of the patient for which to generate data.
     * @param outputStrategy The output strategy to use for outputting the generated data.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
