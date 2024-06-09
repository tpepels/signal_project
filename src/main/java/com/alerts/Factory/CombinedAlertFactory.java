package com.alerts.Factory;

public class CombinedAlertFactory extends AlertFactory {

    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, "Hypotensive Hypoxemia Alert", timestamp);
    }
}