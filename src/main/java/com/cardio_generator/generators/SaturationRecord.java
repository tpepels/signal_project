package com.cardio_generator.generators;

public class SaturationRecord extends PatientRecord {
    private int saturation;

    public SaturationRecord(String patientId, long timestamp, int saturation) {
        super(patientId, timestamp);
        this.saturation = saturation;
    }

    public int getSaturation() {
        return saturation;
    }
}
