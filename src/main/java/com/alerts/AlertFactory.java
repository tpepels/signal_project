package com.alerts;

public class AlertFactory {
    public Alert createAlert(String PatientId, String condition, long timestamp) {
        return new Alert(PatientId, condition, timestamp);
    }
}