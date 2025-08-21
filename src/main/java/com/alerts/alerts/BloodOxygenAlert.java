package com.alerts.alerts;

public class BloodOxygenAlert extends Alert {

    public BloodOxygenAlert(int patientId, String condition, long timestamp) {
    super(patientId, condition, timestamp);
    }

    @Override
    public String toString() {
        return "BloodOxygenAlert [patientId=" + patientId + ", condition=" + condition + ", timestamp=" + timestamp + "]";
    }
}
