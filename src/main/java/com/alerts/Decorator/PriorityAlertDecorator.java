package com.alerts.Decorator;

import com.alerts.Factory.Alert;

public class PriorityAlertDecorator extends AlertDecorator {
    public PriorityAlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert);
    }

    @Override
    public void triggerAlert() {
        System.out.println("High Priority Alert!");
        super.triggerAlert();  // Calls the triggerAlert of the decorated Alert object
    }
}