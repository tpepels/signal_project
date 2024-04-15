package com.cardiogenerator.generators;

import com.cardiogenerator.outputs.OutputStrategy;

public interface PatientDataGenerator {
    void generate(int patientId, OutputStrategy outputStrategy);
}
