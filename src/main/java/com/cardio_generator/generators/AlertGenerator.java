package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;
<<<<<<< HEAD
/**
 * Generates alert data for patients in a health monitoring system.
 * This class simulates alert triggering and resolution based on a probabilistic model.
 * Alerts represent critical health events and are handled in a time-sensitive manner.
 *
 * <p>Alerts have a chance of being triggered randomly, and an active alert has a 90% chance
 * of resolving in the next cycle. This class maintains alert state per patient and outputs
 * relevant alert messages using the specified {@link OutputStrategy}.
 *
 * 
=======

/**
 * Generates simulated alert data for patients.
 * <p>
 * This class simulates the occurrence of alerts for patients. The alerts can either be triggered or resolved,
 * and the state of the alerts is updated and sent to the specified output strategy.
 * </p>
>>>>>>> a3384e7b374c1c493cd3dd27b62bce7aa8160124
 */
public class AlertGenerator implements PatientDataGenerator {
    /**
     * Random generator used for simulating alert triggers and resolutions.
     */
    // changed variable name to camelCase
    public static final Random RANDOM_GENERATOR = new Random();

<<<<<<< HEAD
     /**
     * Tracks alert state for each patient. True means alert is active, false means resolved.
     */
    private boolean[] alertStates; // false = resolved, true = pressed

    /**
     * Constructs a new AlertGenerator with alert states for the specified number of patients.
     *
     * @param patientCount The number of patients being simulated.
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates alert data for a single patient. If the patient is already in an alert state,
     * there is a 90% chance the alert will be resolved. Otherwise, a new alert may be triggered
     * based on a probability model.
     *
     * @param patientId      The unique ID of the patient.
     * @param outputStrategy The output strategy to which the alert data is sent.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                // changed variable name
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
=======
  public static final Random randomGenerator = new Random();
  
  private boolean[] alertStates; // false = resolved, true = pressed
  // change method lower camel case
  /**
   * Constructs an AlertGenerator for a specified number of patients.
   * <p>
   * Initializes the alert states for each patient to false (resolved).
   * </p>
   * 
   * @param patientCount The number of patients to generate alert data for.
   */
  public AlertGenerator(int patientCount) { 
    alertStates = new boolean[patientCount + 1];
  }

  /**
   * Generates alert data for a specific patient and sends it to the provided output strategy.
   * <p>
   * The alert can either be resolved with a 90% probability if it was previously triggered,
   * or it can be triggered based on a Poisson distribution with a specified rate (Lambda).
   * </p>
   * 
   * @param patientId The unique identifier of the patient.
   * @param outputStrategy The strategy used to output the generated data (e.g., console, file, etc.).
   */
  @Override
  public void generate(int patientId, OutputStrategy outputStrategy) {
    try {
      if (alertStates[patientId]) {
        if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
          alertStates[patientId] = false;
          // Output the alert
          outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
>>>>>>> a3384e7b374c1c493cd3dd27b62bce7aa8160124
        }
      } else {
          double Lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
          double p = -Math.expm1(-Lambda); // Probability of at least one alert in the period
          boolean alertTriggered = randomGenerator.nextDouble() < p;
        if (alertTriggered) {
          alertStates[patientId] = true;
          // Output the alert
          outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
        }
      }
    } catch (Exception e) {
      System.err.println("An error occurred while generating alert data for patient " + patientId);
      e.printStackTrace();
    }
  }
}
