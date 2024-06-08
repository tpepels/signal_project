package com.alerts;

public class AlertDecorator extends Alert {
    public AlertDecorator(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }
}
