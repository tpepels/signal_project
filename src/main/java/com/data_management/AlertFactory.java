package com.data_management;

// Base class for alert factories
abstract class AlertFactory {
    public abstract Alert createAlert(String patientId, String condition, long timestamp);
}

// Concrete factory for blood pressure alerts
class BloodPressureAlertFactory extends AlertFactory {
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BloodPressureAlert(patientId, timestamp);
    }
}

// Concrete factory for blood oxygen alerts
class BloodOxygenAlertFactory extends AlertFactory {
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BloodOxygenAlert(patientId, timestamp);
    }
}

// Concrete factory for ECG alerts
class ECGAlertFactory extends AlertFactory {
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new ECGAlert(patientId, timestamp);
    }
}

// Alert base class
abstract class Alert {
    protected String patientId;
    protected long timestamp;

    public Alert(String patientId, long timestamp) {
        this.patientId = patientId;
        this.timestamp = timestamp;
    }
}

// Concrete alert types
class BloodPressureAlert extends Alert {

    public BloodPressureAlert(String patientId, long timestamp) {
        //TODO Auto-generated constructor stub
    } /* Implementation specific details */ }
class BloodOxygenAlert extends Alert {

    public BloodOxygenAlert(String patientId, long timestamp) {
        //TODO Auto-generated constructor stub
    } /* Implementation specific details */ }
class ECGAlert extends Alert {

    public ECGAlert(String patientId, long timestamp) {
        //TODO Auto-generated constructor stub
    } /* Implementation specific details */ }
