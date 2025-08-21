package com.alerts;

// Represents an alert
public class Alert implements AlertInterface {
    private String patientId;
    private String condition;
    private long timestamp;

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

    public String getMessage() {
return "Patient " + patientId + " has a " + condition + " condition at " + timestamp;
    }
}