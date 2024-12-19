package com.alerts;

/**
 * The {@code AlertFactory} class is an abstract factory class for creating {@link Alert} objects.
 * Subclasses should provide an implementation for creating specific types of alerts.
 */
public abstract class AlertFactory {

    /**
     * Creates a new {@link Alert} with the specified patient ID, condition, and timestamp.
     * This method should be implemented by subclasses to create specific types of alerts.
     *
     * @param patientId  the unique identifier of the patient
     * @param condition  the condition that triggered the alert
     * @param timestamp  the time at which the alert was triggered, in milliseconds since the Unix epoch
     * @return a new {@link Alert} instance
     */
    public abstract Alert createAlert(int patientId, String condition, long timestamp);
}

