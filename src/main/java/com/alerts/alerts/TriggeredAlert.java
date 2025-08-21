package com.alerts.alerts;

public class TriggeredAlert extends Alert {
    public TriggeredAlert(int patientId, String condition, long timestamp){
        super(patientId, condition, timestamp);
    }
    @Override
    public String toString() {
        return "TriggeredAlert [patientId=" + patientId + ", condition=" + condition + ", timestamp=" + timestamp + "]";
    }

}
