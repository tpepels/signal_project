package com.alerts;

// Concrete class for a BloodPressureAlert
public class BloodPressureAlert extends Alert {
    public BloodPressureAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

}
