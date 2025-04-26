package com.alerts;

public class OxygenSaturationStrategy implements AlertStrategy {
    @Override
    public Alert checkAlert(String patientId, String metricType, long value) {
        if (metricType.equals("OxygenSaturation") && value < 90) {
            return new Alert(patientId, "Oxygen saturation critical: ", value);
        }
        return null;
    }
}
