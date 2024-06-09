package com.data_management;

public class BloodPressureRecord extends PatientRecord {
    private double systolicPressure;
    private double diastolicPressure;

    public BloodPressureRecord(int patientId, double systolicPressure, double diastolicPressure, long timestamp) {
        super(patientId, 0.0, "BloodPressure", timestamp);
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
    }

    public double getSystolicPressure() {
        return systolicPressure;
    }

    public double getDiastolicPressure() {
        return diastolicPressure;
    }
}
