package com.alerts;

import java.util.List;

import com.data_management.Patient;

public interface AlertCondition {

    /**
     * Returns a list of the relevant alerts for the patient
     * 
     * @param patient the data of the patient
     * @return a list of alerts
     */
    public List<Alert> checkCondition (Patient patient);
}
