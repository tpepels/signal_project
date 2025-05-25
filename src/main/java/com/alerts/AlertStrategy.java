package com.alerts;
import com.data_management.*;

public interface AlertStrategy {
    Alert checkAlert(Patient patient);
}