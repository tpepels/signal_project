package com.alerts;

/**
 * The {@code AlertInterface} interface defines the methods that any alert class
 * should implement. This includes methods to get the patient ID, condition,
 * timestamp, and priority of the alert.
 */
public interface AlertInterface {

    /**
     * Returns the unique identifier of the patient associated with the alert.
     *
     * @return the patient ID
     */
    String getPatientId();

    /**
     * Returns the condition that triggered the alert.
     *
     * @return the condition
     */
    String getCondition();

    /**
     * Returns the timestamp of when the alert was triggered.
     *
     * @return the timestamp, in milliseconds since the Unix epoch
     */
    long getTimestamp();

    /**
     * Returns the priority level of the alert.
     *
     * @return the priority
     */
    String getPriority();
}

