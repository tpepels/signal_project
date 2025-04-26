package com.cardio_generator.outputs;

public interface OutputStrategy {
    void output(int patientId, long timestamp, String label, String data);
}
