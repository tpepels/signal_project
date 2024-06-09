package com.data_management;
import com.alerts.Alert;

public interface AlertStrategy {
    void checkAlert(Patient patient, String recordType);

    void checkIntervals(Patient patient, String recordType);
}
