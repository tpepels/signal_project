package com.cardio_generator.generators;

import java.util.ArrayList;
import java.util.List;

public class ECGRecord extends PatientRecord {
    private int heartRate;
    private List<Double> beatIntervals; // Time intervals between consecutive beats

    public ECGRecord(String patientId, long timestamp, int heartRate, List<Double> beatIntervals) {
        super(patientId, timestamp);
        this.heartRate = heartRate;
        this.beatIntervals = beatIntervals;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public List<Double> getBeatIntervals() {
        return new ArrayList<>(beatIntervals); // Return a copy to prevent modification
    }

    public void evaluateData(Patient patient) {
    evaluateBloodPressureRecords(patient);
    evaluateSaturationRecords(patient);
    evaluateBloodPressureRecords(patient); // Add this call
}

    private void evaluateSaturationRecords(Patient patient) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'evaluateSaturationRecords'");
    }

    private void evaluateBloodPressureRecords(Patient patient) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'evaluateBloodPressureRecords'");
    }

}
