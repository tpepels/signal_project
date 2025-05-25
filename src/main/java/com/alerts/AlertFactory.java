package com.alerts;

abstract class AlertFactory {
    public abstract Alert createAlert(String patientId, String condition, long timestamp);
}