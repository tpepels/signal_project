package com.alerts;

import com.data_management.Patient;
import java.util.ArrayList;
import java.util.List;

public class RepeatedAlertDecorator extends AlertDecorator {
    private final int repeatCount;

    public RepeatedAlertDecorator(AlertCondition alertCondition, int repeatCount) {
        super(alertCondition);
        this.repeatCount = repeatCount;
    }

    @Override
    public List<Alert> checkCondition(Patient patient) {
        List<Alert> allAlerts = new ArrayList<>();
        for (int i = 0; i < repeatCount; i++) {
            allAlerts.addAll(super.checkCondition(patient));
            try {
                Thread.sleep(1000); // Sleep for a specified time interval, e.g., 1000 milliseconds (1 second)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return allAlerts;
    }
}