package com.alerts.alerts;

public class ECGAlert extends Alert {

    public ECGAlert(int patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public String toString() {
        return "ECGAlert [patientId=" + patientId + ", condition=" + condition + ", timestamp=" + timestamp + "]";
    }

}
