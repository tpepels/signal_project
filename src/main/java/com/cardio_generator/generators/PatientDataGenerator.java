package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
<<<<<<< HEAD
/**
 * Interface for generating patient health data.
 * Implementing classes simulate specific types of medical data (e.g., ECG, blood pressure)
 * and output the generated data using a provided {@link OutputStrategy}.
 *
 * <p>This abstraction allows different types of data to be generated consistently for each patient,
 * making it easier to plug in new data generators into the simulation framework.
 * 
 * @see com.cardio_generator.outputs.OutputStrategy
 */
public interface PatientDataGenerator {
    /**
     * Generates data for a specific patient and outputs it using the given strategy.
     *
     * @param patientId      The unique identifier of the patient.
     * @param outputStrategy The strategy used to output the generated data.
=======

/**
 * This interface is called to get basic structure for patient ID and its biometric properties and to get them.
 * These properties are time dependent, they pass over all the metrics contained in the Outputstrategy class, by demand.
 * 
 * */
public interface PatientDataGenerator {
        /**
     * Generates data for a specific patient.
     *
     * @param patientId The unique identifier for the patient.
     * @param outputStrategy The strategy to handle the output of the generated data.
>>>>>>> a3384e7b374c1c493cd3dd27b62bce7aa8160124
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
