package com.alerts;


public class BloodPressureStrategy implements AlertStrategy {
    @Override
    public Alert checkAlert(String patientId, String metricType, long value) {
        if (metricType.equals("SystolicPressure")) {
            if (value >= 140) {
            return new Alert(patientId, "Systolic pressure too high: ", value);
            }
            if (value <= 90) {
                return new Alert(patientId, "Systolic pressure too low: ", value);
            }
        } else if (metricType.equals("DiastolicPressure")) {
            if (value >= 90) {
                return new Alert(patientId, "Diastolic pressure too high: ", value);
            }
            if (value <= 60) {
            return new Alert(patientId, "Diastolic pressure too low: ", value);
            }
        }
        return null;
    }
}
