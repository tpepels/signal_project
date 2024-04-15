package com.cardiogenerator.generators;

import com.cardiogenerator.outputs.OutputStrategy;

/**
 * defines the PatientDataGenerator with only one method - generate()
 */
public interface PatientDataGenerator {
    /**
     * @param patientId
     * @param outputStrategy
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
