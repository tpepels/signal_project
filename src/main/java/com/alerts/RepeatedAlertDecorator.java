package com.alerts;

/**
 * RepeatedAlertDecorator adds a repetition indicator to an existing alert.
 * 
 * This class implements the Decorator pattern by wrapping an {@link AlertComponent}
 * and appending information about how many times the alert has been triggered.
 * 
 * It is useful for highlighting recurring alert conditions, helping to prioritize
 * persistent or escalating patient issues.
 */
public class RepeatedAlertDecorator extends AlertDecorator {
    private final int repeatCount;

    /**
     * Constructs a RepeatedAlertDecorator with the specified alert and repeat count.
     *
     * @param alert the original AlertComponent to wrap
     * @param repeatCount the number of times this alert has been triggered
     */
    public RepeatedAlertDecorator(AlertComponent alert, int repeatCount) {
        super(alert);
        this.repeatCount = repeatCount;
    }

    /**
     * Returns the decorated alert message with the repetition count added.
     *
     * @return a string including the original alert details and the repetition count
     */
    @Override
    public String getDetails() {
        return wrappedAlert.getDetails() + " [Repeated x" + repeatCount + "]";
    }
}
