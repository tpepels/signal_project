package com.alerts;

public class HeartRateStrategy implements AlertStrategy {
    @Override
    public Alert checkAlert(String patientId, String metricType, long value) {
        if (metricType.equals("HeartRate")) {
            if (value < 50) {
                return new Alert(patientId, "Heart rate too low: ", value);
            }
            if (value > 120) {
                return new Alert(patientId, "Heart rate too high: " + value, value);
            }
        }
        return null;
    }
}
