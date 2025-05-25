package com.alerts;

/**
 * PriorityAlertDecorator adds a priority level label to an existing alert.
 * 
 * This class implements the Decorator pattern by wrapping an {@link AlertComponent}
 * and enhancing its description with priority metadata. It is used to distinguish
 * alerts that require more immediate attention.
 *
 * Example use case:
 * - Marking alerts as "HIGH", "MEDIUM", or "LOW" priority.
 */
class PriorityAlertDecorator extends AlertDecorator {
    private final String priorityLevel;

    /**
     * Constructs a PriorityAlertDecorator with a specified base alert and priority level.
     *
     * @param alert the original AlertComponent to wrap
     * @param priorityLevel a string representing the alert's priority (e.g. "high", "low")
     */
    public PriorityAlertDecorator(AlertComponent alert, String priorityLevel) {
        super(alert);
        this.priorityLevel = priorityLevel;
    }

    /**
     * Returns the decorated alert message with the priority prefix added.
     *
     * @return a string including the priority level and the base alert details
     */
    @Override
    public String getDetails() {
        return "[PRIORITY: " + priorityLevel.toUpperCase() + "] " + wrappedAlert.getDetails();
    }
}
