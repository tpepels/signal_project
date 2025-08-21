package com.alerts.alert_strategies;

import java.util.List;

import com.alerts.alerts.Alert;
import com.datamanagement.PatientRecord;

public interface AlertStrategy {

    public List<Alert> checkAlert(int patientId, List<PatientRecord> patientRecords);

}
