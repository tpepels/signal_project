package com.alerts;

public class PriorityAlertDecorator extends AlertDecorator {

    public PriorityAlertDecorator(AlertStrategy strategy) {
        super(strategy);
    }

    @Override
    public Alert checkAlert(String patientId, String metricType, long value) {
        Alert alert = wrappedStrategy.checkAlert(patientId, metricType, value);
        if (alert != null) {
            return new Alert(alert.getPatientId(), metricType, value);
        }
        return null;
    }
}
