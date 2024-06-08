package com.alerts;

public class BloodOxygenAlertFactory extends AlertFactory{

    @Override
    public Alert createAlert(String patientId, String condition, long timeStamp) {
        return new Alert(patientId, "Blood oxygen: " + condition, timeStamp);
    }
}
