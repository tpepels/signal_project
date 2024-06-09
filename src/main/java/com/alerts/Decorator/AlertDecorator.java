package com.alerts.Decorator;

import com.alerts.Factory.Alert;

public abstract class AlertDecorator extends Alert {
    protected Alert decoratedAlert;

    // Constructor requires an instance of Alert to decorate
    public AlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert.getPatientId(), decoratedAlert.getCondition(), decoratedAlert.getTimestamp());
        this.decoratedAlert = decoratedAlert;
    }

    @Override
    public void triggerAlert() {
        decoratedAlert.triggerAlert();  // Default behavior: just forward the call
    }
}
