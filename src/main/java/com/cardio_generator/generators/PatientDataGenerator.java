package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * Defines the contract for patient data generators in a health monitoring system.
 * Implementations of this interface are responsible for generating and sending various
 * types of health-related data for specific patients to a designated output strategy.

 * Usage: Implement this interface to define custom data generation strategies for
 * different types of patient data, such as vital signs, alerts, or other health metrics.
 *
 * @author Tepels
 */
public interface PatientDataGenerator {
    /**
     * Generates data for a specific patient and sends it to the provided output strategy.
     * This method is intended to be implemented by any class that generates patient data,
     * where the data generation logic and the specifics of the data types are defined by
     * the implementor
     *
     * @param patientId the unique identifier for the patient for whom data is being generated
     * @param outputStrategy the output mechanism to which the generated data will be sent
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
