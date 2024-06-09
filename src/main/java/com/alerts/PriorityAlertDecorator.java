package com.alerts;

import com.data_management.Patient;
import java.util.List;

public class PriorityAlertDecorator extends AlertDecorator {
    public PriorityAlertDecorator(AlertCondition alertCondition) {
        super(alertCondition);
    }

    @Override
    public List<Alert> checkCondition(Patient patient) {
        List<Alert> alerts = super.checkCondition(patient);
        // Update priority based on some internal logic
        alerts.forEach(alert -> alert.setPriority(updatePriority(alert.getPriority())));
        return alerts;
    }

    // Simulated method to incrementally update the priority
    private String updatePriority(String currentPriority) {
        if (currentPriority == null) {
            return "Low";
        } else if ("Low".equals(currentPriority)) {
            return "Medium";
        } else if ("Medium".equals(currentPriority)) {
            return "High";
        } else {
            return "Critical";  // Max priority reached
        }
    }

    // Method to directly set the priority
    public void setPriorityForAlerts(List<Alert> alerts, String priority) {
        alerts.forEach(alert -> alert.setPriority(priority));
    }
}
