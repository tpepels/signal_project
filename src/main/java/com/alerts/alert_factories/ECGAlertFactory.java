package com.alerts.alert_factories;

import com.alerts.alerts.Alert;
import com.alerts.alerts.ECGAlert;

public class ECGAlertFactory implements AlertFactory {

    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
    return new ECGAlert(Integer.parseInt(patientId), condition, timestamp);
    }
}
