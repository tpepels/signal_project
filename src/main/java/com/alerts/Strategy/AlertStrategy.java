package com.alerts.Strategy;

import com.data_management.Patient;

public interface AlertStrategy {
    boolean checkAlert(Patient patient);
}