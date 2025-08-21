package com.alerts.alerts;

public class BloodPressureAlert extends Alert {

    public BloodPressureAlert(int patientId, String condition, long timestamp) {
       super(patientId,condition,timestamp);
    }

    @Override
    public String toString() {
        return "BloodPressureAlert [patientId=" + patientId + ", condition=" + condition + ", timestamp=" + timestamp + "]";
    }
}
