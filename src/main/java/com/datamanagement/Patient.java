package com.datamanagement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a patient and manages their medical records.
 * This class stores patient-specific data, allowing for the addition and
 * retrieval
 * of medical records based on specified criteria.
 */
public class Patient {
    private final int patientId;
    private final List<PatientRecord> patientRecords;
    private long startTime;
    private long endTime;

    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
        this.startTime = 0;
        this.endTime = 0;
    }

    private void updateTimes() {
        if (patientRecords.isEmpty()) {
            startTime = 0;
            endTime   = 0;
        } else {
            // earliest = index 0, latest = last index
            startTime = patientRecords.get(0).getTimestamp();
            endTime   = patientRecords.get(patientRecords.size() - 1).getTimestamp();
        }
    }

    public int getPatientId() {
        return patientId;
    }

    public List<PatientRecord> getPatientRecords() {
        return patientRecords;
    }

    public void addRecord(Object measurementValue, String recordType, long timestamp) {
        PatientRecord record = new PatientRecord(patientId, measurementValue, recordType, timestamp);
        patientRecords.add(record);
        updateTimes();
    }

    public List<PatientRecord> getRecords(long startTime, long endTime) {
        List<PatientRecord> records = new ArrayList<>();
        for (PatientRecord record : patientRecords) {
            long t = record.getTimestamp();
            if (t >= startTime && t <= endTime) {
                records.add(record);
            }
        }
        return records;
    }
}
