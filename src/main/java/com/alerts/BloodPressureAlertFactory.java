package com.alerts;
public class BloodPressureAlertFactory extends AlertFactory {


    @Override
    public Alert createAlert(String patientId, String condition, long timeStamp) {
        return new Alert(patientId, "Blood pressure: " + condition, timeStamp);
    }
}
