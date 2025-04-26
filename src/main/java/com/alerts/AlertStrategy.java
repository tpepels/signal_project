package com.alerts;

public interface AlertStrategy {
    public Alert checkAlert(String patientId, String metricType, long value);
}
