package com.cardiogenerator.generators;

import com.cardiogenerator.outputs.OutputStrategy;

/** Represents a generator for simulated patient data */
public interface PatientDataGenerator {

    /**
     * Generates and outputs simulated data for a specified patient.
     *
     * @param patientId The ID of the patient for whom data is being generated.
     * @param outputStrategy The strategy used for outputting the generated data.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
