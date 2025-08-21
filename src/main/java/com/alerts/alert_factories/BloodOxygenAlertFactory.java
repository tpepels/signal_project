package com.alerts.alert_factories;

import com.alerts.alerts.Alert;
import com.alerts.alerts.BloodOxygenAlert;

public class BloodOxygenAlertFactory implements AlertFactory {

    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BloodOxygenAlert(Integer.parseInt(patientId), condition, timestamp);
    }
}
