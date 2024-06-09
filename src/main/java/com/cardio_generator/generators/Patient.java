package com.cardio_generator.generators;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Represents a patient with an identifier and a list of health records.
 */
public class Patient {
    private String patientId; // Unique identifier for the patient
    private List<PatientRecord> records; // List to store health records for the patient

    /**
     * Constructs a new Patient with a given patient ID.
     * @param patientId The unique identifier for the patient.
     */
    public Patient(String patientId) {
        this.patientId = patientId;
        this.records = new ArrayList<>(); // Initialize the list of health records
    }

    /**
     * Retrieves the patient ID.
     * @return The unique identifier of the patient.
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Retrieves the patient's records within a specified time frame.
     * @param startTime The start time for the record retrieval, in milliseconds since epoch.
     * @param endTime The end time for the record retrieval, in milliseconds since epoch.
     * @return A list of PatientRecord objects that fall within the specified time range.
     */
    public List<PatientRecord> getRecords(long startTime, long endTime) {
        return records.stream()
                      .filter(record -> record.getTimestamp() >= startTime && record.getTimestamp() <= endTime)
                      .collect(Collectors.toList());
    }

    /**
     * Adds a health record to the patient's list of records.
     * @param record The PatientRecord to be added.
     */
    public void addRecord(PatientRecord record) {
        records.add(record); // Add the new record to the list
    }
}
