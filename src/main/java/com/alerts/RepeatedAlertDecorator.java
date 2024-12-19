package com.alerts;

/**
 * The {@code RepeatedAlertDecorator} class is a concrete decorator for adding repetition information to alerts.
 * It extends the {@link AlertDecorator} class to add additional functionality to the base {@link Alert} object.
 */
public class RepeatedAlertDecorator extends AlertDecorator {
    private final int repeatCount;

    /**
     * Constructs a new {@code RepeatedAlertDecorator} with the specified {@link Alert} to be decorated
     * and the number of repetitions to be added.
     *
     * @param decoratedAlert the alert instance to be decorated
     * @param repeatCount    the number of times the alert has been repeated
     */
    public RepeatedAlertDecorator(Alert decoratedAlert, int repeatCount) {
        super(decoratedAlert);
        this.repeatCount = repeatCount;
    }

    /**
     * Returns a string representation of the decorated alert, including the repetition count.
     *
     * @return a string representation of the alert with the repetition count
     */
    @Override
    public String toString() {
        return decoratedAlert.toString() + ", Repeated " + repeatCount + " times";
    }
}
