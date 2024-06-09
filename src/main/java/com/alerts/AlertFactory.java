package com.alerts;

public abstract class AlertFactory {

    public Alert createAlert(String patientId, String condition, long timeStamp) {
        Alert alert = new Alert(patientId, condition, timeStamp);
        return alert;
    }

}
