package com.alerts;

/**
 * The {@code AlertDecorator} class is a base class for all alert decorators.
 * It extends the {@link Alert} class and allows additional functionality to be
 * dynamically added to an existing {@link Alert} instance.
 */
public class AlertDecorator extends Alert {
    protected Alert decoratedAlert;

    /**
     * Constructs a new {@code AlertDecorator} with the specified {@link Alert} to be decorated.
     *
     * @param decoratedAlert the alert instance to be decorated
     */
    public AlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert.getPatientId(), decoratedAlert.getCondition(), decoratedAlert.getTimestamp());
        this.decoratedAlert = decoratedAlert;
    }

    /**
     * Returns the unique identifier of the patient from the decorated alert.
     *
     * @return the patient ID
     */
    @Override
    public int getPatientId() {
        return decoratedAlert.getPatientId();
    }

    /**
     * Returns the condition that triggered the alert from the decorated alert.
     *
     * @return the condition
     */
    @Override
    public String getCondition() {
        return decoratedAlert.getCondition();
    }

    /**
     * Returns the timestamp of when the alert was triggered from the decorated alert.
     *
     * @return the timestamp, in milliseconds since the Unix epoch
     */
    @Override
    public long getTimestamp() {
        return decoratedAlert.getTimestamp();
    }

    /**
     * Returns a string representation of the decorated alert.
     *
     * @return a string representation of the alert
     */
    @Override
    public String toString() {
        return decoratedAlert.toString();
    }
}
