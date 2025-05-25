package com.alerts;

class BasicAlertComponent implements AlertComponent {
    private final Alert alert;

    public BasicAlertComponent(Alert alert) {
        this.alert = alert;
    }

    @Override
    public String getPatientId() {
        return alert.getPatientId();
    }

    @Override
    public String getCondition() {
        return alert.getCondition();
    }

    @Override
    public long getTimestamp() {
        return alert.getTimestamp();
    }

    @Override
    public String getDetails() {
        return "[Patient: " + getPatientId() + "] " + getCondition() + " @ " + getTimestamp();
    }
}
