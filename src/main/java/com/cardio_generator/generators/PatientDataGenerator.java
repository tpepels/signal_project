package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * This interface defines the method to generate patient data.
 */
public interface PatientDataGenerator {

    /**
     * Generates patient data for the specified patient ID.
     *
     * @param patientId the patient ID
     * @param outputStrategy the output strategy to use
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
