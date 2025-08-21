package com.alerts.alert_decorators;

import com.alerts.alert_strategies.AlertStrategy;
import com.alerts.alerts.Alert;
import com.datamanagement.PatientRecord;

import java.util.List;

public abstract class AlertDecorator implements AlertStrategy {
AlertStrategy alertStrategy;

public AlertDecorator(AlertStrategy alertStrategy) {
    this.alertStrategy = alertStrategy;
}
    @Override
    public List<Alert> checkAlert(int patientId, List<PatientRecord> patientRecords) {
        return alertStrategy.checkAlert(patientId, patientRecords);
    }
}
