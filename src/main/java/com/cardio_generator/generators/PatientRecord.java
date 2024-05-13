package com.cardio_generator.generators;

/**
 * Represents a single record of patient data.
 * This class is designed to store information about a specific measurement for a patient.
 */
public class PatientRecord {
    private String patientId;  // Unique identifier for the patient to whom this record belongs
    private long timestamp;    // Timestamp when the record was created, in milliseconds since the epoch
    private String recordType; // Type of record (e.g., "HeartRate", "BloodPressure")
    private double measurementValue;  // The value of the measurement, assumed to be a double for general use

    /**
     * Constructs a new PatientRecord with specified details.
     * @param patientId The unique identifier of the patient.
     * @param timestamp The time when the record was created, represented as milliseconds since epoch.
     * @param recordType The type of the medical record, such as "HeartRate" or "BloodPressure".
     * @param measurementValue The numerical value of the record measurement.
     */
    public PatientRecord(String patientId, long timestamp, String recordType, double measurementValue) {
        this.patientId = patientId;
        this.timestamp = timestamp;
        this.recordType = recordType;
        this.measurementValue = measurementValue;
    }

    public PatientRecord(String patientId2, String timestamp2, int heartRate, int systolic, int diastolic,
            int oxygenSaturation) {
        //TODO Auto-generated constructor stub
    }

    /**
     * Returns the patient identifier associated with this record.
     * @return A string representing the patient's unique identifier.
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Returns the timestamp when the record was created.
     * @return The long value of the timestamp in milliseconds since the epoch.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the type of the record.
     * @return A string representing the type of the record (e.g., "HeartRate").
     */
    public String getRecordType() {
        return recordType;
    }

    /**
     * Returns the measurement value of the record.
     * @return A double representing the value of the measurement taken.
     */
    public double getMeasurementValue() {
        return measurementValue;
    }

    // Constructor that matches the expected parameters
    public PatientRecord(String patientId, long timestamp) {
        this.patientId = patientId;
        this.timestamp = timestamp;
    }

    
}


