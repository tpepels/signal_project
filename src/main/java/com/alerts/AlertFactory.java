package com.alerts;

public abstract class AlertFactory {

    public Alert createAlert(String patientId, String condition, long timeStamp) {
        return new Alert(patientId, condition, timeStamp);
    }

}
