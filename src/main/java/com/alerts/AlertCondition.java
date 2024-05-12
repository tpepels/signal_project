package com.alerts;

import java.util.List;

import com.data_management.Patient;

public interface AlertCondition {
    public List<Alert> checkCondition (Patient patient);
}
