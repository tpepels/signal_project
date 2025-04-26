package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

public interface PatientDataGenerator {
    void generate(int patientId, OutputStrategy outputStrategy);
}
