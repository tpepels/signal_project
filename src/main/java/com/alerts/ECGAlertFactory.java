package com.alerts;

public class ECGAlertFactory extends AlertFactory{

    @Override
    public Alert createAlert(String patientId, String condition, long timeStamp) {
        return new Alert(patientId, "ECG: " + condition, timeStamp);
    }
}
