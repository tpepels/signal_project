package com.alerts;

public class RepeatedAlertDecorator extends AlertDecorator {

    private static final int REPEAT_INTERVAL = 3;
    private int counter = 0;

    public RepeatedAlertDecorator(AlertStrategy strategy) {
        super(strategy);
    }

    @Override
    public Alert checkAlert(String patientId, String metricType, long value) {
        counter++;
        if (counter % REPEAT_INTERVAL == 0) {
            return wrappedStrategy.checkAlert(patientId, metricType, value);
        }
        return null;
    }
}
