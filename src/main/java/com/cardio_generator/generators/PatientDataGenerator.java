package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * the PatienDataGenerator defines condition for generating data for patients for the cardio_generator system
 * the patien data gives the necessary data to generate further information
 * @param patientid is the id of a patient
 * @param outputStrategy is the choosen output strategy
 */
public interface PatientDataGenerator {
    void generate(int patientId, OutputStrategy outputStrategy);
}
