package com.alerts;

/**
 * Represents an alert generated for a patient based on specific conditions.
 */
public class Alert {
    private String patientId;  // Unique identifier for the patient associated with the alert
    private String condition;  // Description of the medical or health condition triggering the alert
    private long timestamp;    // Time at which the alert was generated (in milliseconds since epoch)

    /**
     * Constructs a new Alert with the specified patient ID, condition, and timestamp.
     * @param patientId The unique identifier of the patient for whom the alert is generated.
     * @param condition A string describing the health condition that triggered the alert.
     * @param timestamp The timestamp when the alert was generated, typically when the condition was detected.
     */
    public Alert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    /**
     * Returns the patient ID associated with this alert.
     * @return A string representing the patient's unique identifier.
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Returns the condition that triggered the alert.
     * @return A string describing the medical or health condition.
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Returns the timestamp at which the alert was generated.
     * @return The long timestamp value indicating when the alert was generated.
     */
    public long getTimestamp() {
        return timestamp;
    }
}

