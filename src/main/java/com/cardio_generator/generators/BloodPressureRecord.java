package com.cardio_generator.generators;

/**
 * Represents a specific type of patient record that stores blood pressure measurements.
 * This class extends the PatientRecord class by including details specific to blood pressure,
 * namely systolic and diastolic pressures.
 */
public class BloodPressureRecord extends PatientRecord {
    private int systolicPressure; // The systolic blood pressure measurement (the top number in a blood pressure reading)
    private int diastolicPressure; // The diastolic blood pressure measurement (the bottom number in a blood pressure reading)

    /**
     * Constructs a new BloodPressureRecord with detailed blood pressure information.
     * This constructor initializes the PatientRecord superclass with the patient ID, timestamp,
     * and a calculated average value of systolic and diastolic pressures for generalized measurement handling.
     *
     * @param patientId The unique identifier of the patient.
     * @param timestamp The time when the blood pressure was recorded, in milliseconds since the epoch.
     * @param systolicPressure The systolic blood pressure of the patient.
     * @param diastolicPressure The diastolic blood pressure of the patient.
     */
    public BloodPressureRecord(String patientId, long timestamp, int systolicPressure, int diastolicPressure) {
        super(patientId, timestamp, "BloodPressure", (systolicPressure + diastolicPressure) / 2.0); // Example aggregation
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
    }

    /**
     * Returns the systolic blood pressure measurement.
     * @return An integer representing the systolic blood pressure.
     */
    public int getSystolicPressure() {
        return systolicPressure;
    }

    /**
     * Returns the diastolic blood pressure measurement.
     * @return An integer representing the diastolic blood pressure.
     */
    public int getDiastolicPressure() {
        return diastolicPressure;
    }
}

