package com.alerts.alert_factories;

import com.alerts.alerts.Alert;
import com.alerts.alerts.BloodPressureAlert;

public class BloodPressureAlertFactory implements AlertFactory {
    Alert alert;

    public BloodPressureAlertFactory(Alert alert) {
        this.alert = alert;
    }

    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BloodPressureAlert(Integer.parseInt(patientId), condition, timestamp);


    }
}
