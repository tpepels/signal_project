package com.data_management;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int patientId;
    private List<PatientRecord> patientRecords;

    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
    }

    public void addRecord(double measurementValue, String recordType, long timestamp) {
        PatientRecord record = new PatientRecord(this.patientId, measurementValue, recordType, timestamp);
        this.patientRecords.add(record);
    }

    /**
     * Retrieves a list of PatientRecord objects for this patient that fall within a
     * specified time range.
     * The method filters records based on the start and end times provided.
     *
     * @param startTime the start of the time range, in milliseconds since UNIX
     *                  epoch
     * @param endTime   the end of the time range, in milliseconds since UNIX epoch
     * @return a list of PatientRecord objects that fall within the specified time
     *         range
     */
    public List<PatientRecord> getRecords(long startTime, long endTime) {
        List<PatientRecord> recordsWithinTimeFrame = new ArrayList<>();
        for (PatientRecord record : patientRecords) {
            long recordTimestamp = record.getTimestamp();
            if (recordTimestamp >= startTime && recordTimestamp <= endTime) {
                recordsWithinTimeFrame.add(record);
            }
        }
        return recordsWithinTimeFrame;
    }
}