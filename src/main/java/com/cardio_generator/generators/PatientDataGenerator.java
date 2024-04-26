package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The PatientDataGenerator interface represents a generator for patient data.
 * Implementing classes must define a method to generate data for a specific patient.
 */
public interface PatientDataGenerator {
    
    /**
     * Generates data for the specified patient and outputs it using the given output strategy.
     * 
     * @param patientId      The ID of the patient for whom data is generated.
     * @param outputStrategy The output strategy used to output the generated data.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
