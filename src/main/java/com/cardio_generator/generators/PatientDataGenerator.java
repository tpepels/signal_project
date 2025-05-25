package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Interface for generating data for a specific patient. Implementing classes should define the logic
 * for generating various types of data (ECG, blood pressure, etc.) for a patient and outputting 
 * that data using the specified output strategy.
 *
 * @param patientId The ID of the patient for which the data is being generated.
 * @param outputStrategy The strategy used for outputting the generated data.
 */

public interface PatientDataGenerator {
    void generate(int patientId, OutputStrategy outputStrategy);
}
