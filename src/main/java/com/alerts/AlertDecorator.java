package com.alerts;

public abstract class AlertDecorator implements AlertInterface {

    protected AlertInterface decoratedAlert;

    public AlertDecorator(AlertInterface alert) {
        this.decoratedAlert = alert;
    }

    public String getPatientId() {
        return decoratedAlert.getPatientId();
    }

    public String getCondition() {
        return decoratedAlert.getCondition();
    }

    public long getTimestamp() {
        return decoratedAlert.getTimestamp();
    }

    public String getMessage() {
        return decoratedAlert.getMessage();
    }
}