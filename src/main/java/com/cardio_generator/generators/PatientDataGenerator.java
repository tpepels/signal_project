package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * Defines the contract for generating patient data in the cardio generator system.
 *Implementations of this interface are responsible for producing specific types of
 * patient data (such as ECG, blood pressure, or saturation) and outputting them using
 * the provided {@link OutputStrategy}. This interface enables extensibility and modularity
 * in the data generation pipeline.
 *Usage: Implement this interface for each type of patient data you wish to generate.
 * The {@code generate} method will be called with a patient identifier and an output
 * strategy to handle the generated data.
 */
public interface PatientDataGenerator {
    /**
     * Generates data for a specific patient and outputs it using the provided strategy.
     * This method may be called repeatedly for one
     * patient to simulate continuous or periodic data generation.
     *
     * @param patientId the unique identifier of the patient for whom data is generated;
     *                  must be a positive integer corresponding to a valid patient in the system
     * @param outputStrategy the strategy used to output the generated data; must not be {@code null}
     * @throws IllegalArgumentException if {@code patientId} is invalid or {@code outputStrategy} git add PatientDataGenerator.javais {@code null}
     * @throws RuntimeException if an error occurs during data generation or output
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
