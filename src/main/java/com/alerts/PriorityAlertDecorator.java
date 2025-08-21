package com.alerts;

import java.util.List;

public class PriorityAlertDecorator extends AlertDecorator {

    private static final List<String> PRIORITY_LEVELS = List.of("LOW", "MEDIUM", "HIGH", "CRITICAL");
    private int priorityIndex;

    public PriorityAlertDecorator(AlertInterface alert, int priorityIndex) {
        super(alert);
        this.priorityIndex = Math.min(Math.max(priorityIndex, 0), PRIORITY_LEVELS.size() - 1);
    }

    @Override
    public String getMessage() {
        return "PRIORITY: " + PRIORITY_LEVELS.get(priorityIndex) + " " + decoratedAlert.getMessage();
    }

    public void setPriority(int priorityIndex) {
        this.priorityIndex = Math.min(Math.max(priorityIndex, 0), PRIORITY_LEVELS.size() - 1);
    }

    public String getPriority() {
        return PRIORITY_LEVELS.get(priorityIndex);
    }
}