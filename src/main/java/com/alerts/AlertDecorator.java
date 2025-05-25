package com.alerts;

abstract class AlertDecorator implements AlertComponent {
    protected AlertComponent wrappedAlert;

    public AlertDecorator(AlertComponent alert) {
        this.wrappedAlert = alert;
    }

    @Override
    public String getPatientId() {
        return wrappedAlert.getPatientId();
    }

    @Override
    public String getCondition() {
        return wrappedAlert.getCondition();
    }

    @Override
    public long getTimestamp() {
        return wrappedAlert.getTimestamp();
    }

    @Override
    public String getDetails() {
        return wrappedAlert.getDetails();
    }
}