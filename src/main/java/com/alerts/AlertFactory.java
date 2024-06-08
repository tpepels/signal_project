package com.alerts;

public abstract class AlertFactory {
    private Alert alert;

    public abstract Alert createAlert(String patientId, String condition, long timeStamp);

}
