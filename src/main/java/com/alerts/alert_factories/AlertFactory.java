package com.alerts.alert_factories;

import com.alerts.alerts.Alert;

public interface AlertFactory {

    public Alert createAlert(String patientId, String condition, long timestamp);
}
