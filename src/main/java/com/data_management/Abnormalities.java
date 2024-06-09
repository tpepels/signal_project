package com.data_management;

public interface Abnormalities {
    class patientState{
        boolean diastolicMin;
        boolean diastolicMax;
        boolean systolicMin;
        boolean systolicMax;
        boolean lowSaturation;
        boolean abnormalHeartRate;

    }

    void update(Patient patient, patientState state);

    void update(PatientRecord rec, patientState state);
}
