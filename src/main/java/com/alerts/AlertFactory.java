package com.alerts;

public class AlertFactory {

    public static void main(String[] args) {
        AlertFactory test = new BloodPressureAlertFactory();
        Alert test2 = test.createAlert("0", "Test", 1000);
        System.out.println(test2.getClass());
    }
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new Alert(patientId, condition, timestamp);
    }
}
