package com.alerts;

// Represents an alert
public class Alert {
    private String patientId;
    private String condition;
    private long timestamp;
    private String priority;

    public Alert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getCondition() {
        return condition;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String Priority) {
        this.priority = Priority;
    }
}