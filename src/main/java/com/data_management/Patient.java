package com.data_management;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a patient and manages their medical records.
 * This class stores patient-specific data, allowing for the addition and
 * retrieval
 * of medical records based on specified criteria.
 */
public class Patient {
    private int patientId;
    private List<PatientRecord> patientRecords;

    /**
     * Constructs a new Patient with a specified ID.
     * Initializes an empty list of patient records.
     *
     * @param patientId the unique identifier for the patient
     */
    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
    }

    public String getPatientId() {
        return String.valueOf(patientId);
    }
    public int getPatientIdAsInt() {
        return patientId;
    }

    /**
     * Adds a new record to this patient's list of medical records.
     * The record is created with the specified measurement value, record type, and
     * timestamp.
     *
     * @param measurementValue the measurement value to store in the record
     * @param recordType       the type of record, e.g., "HeartRate",
     *                         "BloodPressure"
     * @param timestamp        the time at which the measurement was taken, in
     *                         milliseconds since UNIX epoch
     */
    public void addRecord(double measurementValue, String recordType, long timestamp) {
        PatientRecord rec = new PatientRecord(this.patientId, measurementValue, recordType, timestamp);
        this.patientRecords.add(rec);
    }

    /**
     * Retrieves a list of PatientRecord objects for this patient that fall within a
     * specified time range.
     * The method filters records based on the start and end times provided.
     *
     * @return a list of PatientRecord objects that fall within the specified time
     * range
     */
    public List<PatientRecord> getRecords() {
        // getting patient records as a stream
        return patientRecords.stream()
                .filter(record -> record.getTimestamp() >= startTime && record.getTimestamp() <= endTime)
                .collect(Collectors.toList());
       }


    }
