package com.alerts.alert_decorators;

import com.alerts.alert_strategies.AlertStrategy;
import com.alerts.alerts.Alert;
import com.datamanagement.Patient;
import com.datamanagement.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class RepeatedAlertDecorator extends AlertDecorator {
    protected long startTime;
    protected long endTime;
    protected Patient patient;

    public RepeatedAlertDecorator(Patient patient, AlertStrategy alertStrategy, long startTime, long endTime) {
        super(alertStrategy);
        this.startTime = startTime;
        this.endTime = endTime;
        this.patient = patient;
    }

    public List<Alert> repeatedAlerts() {
        List<PatientRecord> records = patient.getRecords(startTime, endTime);
        List<Alert> alertList =  alertStrategy.checkAlert(patient.getPatientId(), records);
        for (Alert alert : alertList) {
        alert.setCondition("REPEATED: " + alert.getCondition());
        }
        return alertList;
    }






}
