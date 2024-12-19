package com.alerts;

/**
 * The {@code BloodOxygenAlertFactory} class is a concrete implementation of the {@link AlertFactory} class.
 * It is responsible for creating {@link Alert} objects specifically for blood oxygen-related conditions.
 */
public class BloodOxygenAlertFactory extends AlertFactory {

    /**
     * Creates an {@link Alert} object with the specified parameters.
     *
     * @param patientId  the unique identifier of the patient
     * @param condition  the condition that triggered the alert
     * @param timestamp  the time at which the alert was triggered, in milliseconds since the Unix epoch
     * @return the created {@link Alert} object
     */
    @Override
    public Alert createAlert(int patientId, String condition, long timestamp) {
        return new Alert(patientId, condition, timestamp);
    }
}

