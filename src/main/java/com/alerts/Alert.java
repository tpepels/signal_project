package com.alerts;

/**
 * Represents an alert triggered by certain conditions in patient data.
 * This class stores the details of the alert, including the patient ID, the condition that triggered the alert,
 * and the timestamp of when the alert was triggered.
 */
public class Alert {
    private final int patientId;
    private final String condition;
    private final long timestamp;

    /**
     * Constructs a new Alert with the specified patient ID, condition, and timestamp.
     *
     * @param patientId  the unique identifier of the patient
     * @param condition  the condition that triggered the alert
     * @param timestamp  the time at which the alert was triggered, in milliseconds since the Unix epoch
     */
    public Alert(int patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    /**
     * Returns the unique identifier of the patient.
     *
     * @return the patient ID
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Returns the condition that triggered the alert.
     *
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Returns the timestamp of when the alert was triggered.
     *
     * @return the timestamp, in milliseconds since the Unix epoch
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns a string representation of the alert.
     *
     * @return a string representation of the alert
     */
    @Override
    public String toString() {
        return "Alert{" +
                "patientId=" + patientId +
                ", condition='" + condition + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
