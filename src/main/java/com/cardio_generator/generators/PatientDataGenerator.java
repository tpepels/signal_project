package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * Represents a generator for patient data in a healthcare simulation system.
 * Classes implementing this interface are responsible for generating specific types of data
 * for patients and using an output strategy to handle the results.
 */
public interface PatientDataGenerator {
    /**
     * Generates data for a specific patient and outputs it using the provided output strategy.
     * This method is intended to be implemented to handle various types of data generation
     *
     * @param patientId The ID of the patient for whom data is to be generated.
     * @param outputStrategy The strategy used for outputting the generated data.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
