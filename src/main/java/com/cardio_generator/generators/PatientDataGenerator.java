package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Defines a strategy for generating data.
 * Classes implementing this interface can specify their own method for generating data of any type.
 */
public interface PatientDataGenerator {
    /**
     * Generates and outputs data for a patient.
     * 
     * @param patientId patientId linked to patient.
     * @param outputStrategy The strategy used for outputting the alert.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
