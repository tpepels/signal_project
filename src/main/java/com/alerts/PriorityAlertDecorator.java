package com.alerts;

/**
 * The {@code PriorityAlertDecorator} class is a concrete decorator for adding priority levels to alerts.
 * It extends the {@link AlertDecorator} class to add additional functionality to the base {@link Alert} object.
 */
public class PriorityAlertDecorator extends AlertDecorator {
    private final int priorityLevel;

    /**
     * Constructs a new {@code PriorityAlertDecorator} with the specified {@link Alert} to be decorated
     * and the priority level to be added.
     *
     * @param decoratedAlert the alert instance to be decorated
     * @param priorityLevel  the priority level to be added to the alert
     */
    public PriorityAlertDecorator(Alert decoratedAlert, int priorityLevel) {
        super(decoratedAlert);
        this.priorityLevel = priorityLevel;
    }

    /**
     * Returns a string representation of the decorated alert, including the priority level.
     *
     * @return a string representation of the alert with the priority level
     */
    @Override
    public String toString() {
        return decoratedAlert.toString() + ", Priority Level " + priorityLevel;
    }
}
