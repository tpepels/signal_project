package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * interface that declares a method to generate data
 */
public interface PatientDataGenerator {

    /**
     * Generates data for a given patient following a gicen strategy
     * @param patientId patient identifier (as an integer)
     * @param outputStrategy strategy of the output data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
