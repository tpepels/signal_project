package com.alerts;

import java.util.List;

import com.data_management.Patient;

public interface AlertStrategy {
    public List<Alert> checkAlert(Patient patient);
}
