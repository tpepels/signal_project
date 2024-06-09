package com.alerts;

public class PriorityAlertDecorator extends AlertDecorator {
    private final String priority;

    public PriorityAlertDecorator(Alert decoratedAlert, String priority) {
        super(decoratedAlert);
        this.priority = priority;
    }

    @Override
    public String getCondition() {
        return priority + " PRIORITY: " + super.getCondition();
    }
}
