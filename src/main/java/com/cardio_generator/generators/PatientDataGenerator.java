package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

public interface PatientDataGenerator {

    /**
     * Generates health data for the specified patient ID using the provided output method.
     *
     * @param patientId      The ID of the patient 
     * @param outputStrategy The output strategy used to output the generated data.
     */

    void generate(int patientId, OutputStrategy outputStrategy);
}
