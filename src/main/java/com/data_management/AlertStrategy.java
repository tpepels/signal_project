package com.data_management;

// Strategy interface
interface AlertStrategy {
    boolean checkAlert(String patientData);
}

// Concrete strategy for blood pressure
class BloodPressureStrategy implements AlertStrategy {
    public boolean checkAlert(String patientData) {
        // Logic to determine if an alert should be triggered
        return true; // Simplified example
    }
}

// Concrete strategy for heart rate
class HeartRateStrategy implements AlertStrategy {
    public boolean checkAlert(String patientData) {
        return false; // Simplified example
    }
}

// Concrete strategy for oxygen saturation
class OxygenSaturationStrategy implements AlertStrategy {
    public boolean checkAlert(String patientData) {
        return true; // Simplified example
    }
}
