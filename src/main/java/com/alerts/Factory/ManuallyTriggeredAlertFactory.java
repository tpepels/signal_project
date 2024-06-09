package com.alerts.Factory;

public class ManuallyTriggeredAlertFactory extends AlertFactory {

    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, "Manually Triggered Alert", timestamp);
    }
}
