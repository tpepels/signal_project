package com.alerts.alerts;

public class HypotensiveHypoxemiaAlert extends Alert {

    public HypotensiveHypoxemiaAlert(int patientId, String condition, long timestamp) {
        super(patientId,condition,timestamp);
    }

    @Override
    public String toString() {
        return "BloodOxygenAlert [patientId=" + patientId + ", condition=" + condition + ", timestamp=" + timestamp + "]";
    }

}
