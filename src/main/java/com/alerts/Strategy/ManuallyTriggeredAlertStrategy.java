package com.alerts.Strategy;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public class ManuallyTriggeredAlertStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(Patient patient) {
        List<PatientRecord> lastManuallyTriggeredList = patient.lastTenMinutesOfType("Alert");
        if (!lastManuallyTriggeredList.isEmpty()) {
            PatientRecord lastTriggered = lastManuallyTriggeredList.get(lastManuallyTriggeredList.size() - 1);
            return lastTriggered.getMeasurementValue() == 1.0;
        }
        return false;
    }
}
