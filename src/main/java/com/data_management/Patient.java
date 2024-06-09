package com.data_management;

import java.util.ArrayList;
import java.util.List;

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
        List<PatientRecord>ans = new ArrayList<>();
        for(int i = 0;startTime<=endTime&&i<patientRecords.size();i++,startTime++){
            ans.add(this.patientRecords.get(i));
        }
      return ans;
    }

    public long getEndTime(){
        return patientRecords.get(patientRecords.size()-1).getTimestamp();
    }

    public long getPrevThreeTime(){
        return patientRecords.get(patientRecords.size()-3).getTimestamp();
    }
    public int getPatientId(){
        return this.patientId;
    }
    public long getStartTime(){
        return patientRecords.get(0).getTimestamp();
    }

    public List<PatientRecord>lastTenMinutesOfType(String type){
        List<PatientRecord>returnList = new ArrayList<>();
        long endTime = patientRecords.get(patientRecords.size()-1).getTimestamp();
        long stopTime = endTime-600000L;
        for(int i = patientRecords.size()-1;i>=0;i--){
            if(patientRecords.get(i).getTimestamp()<=stopTime){
                return returnList;
            }
            if(patientRecords.get(i).getRecordType().equals(type)){
                returnList.add(patientRecords.get(i));
            }
        }
        return returnList;
    }

    public PatientRecord getECGRecord(){
        for(int i = patientRecords.size()-1;i>=0;i--){
            if(patientRecords.get(i).getRecordType().equals("ECG")){
                return patientRecords.get(i);
            }

        }
        return null;
    }
    public List<PatientRecord> getLastThreeBloodPressure(String type) {
        List<PatientRecord> patientRecordList = this.getRecords(this.getStartTime(), this.getEndTime());
        List<PatientRecord> lastThree = new ArrayList<>();
        int count = 0; // To keep track of how many matching records we've added

        // Iterate backwards through the patientRecordList
        for (int i = patientRecordList.size() - 1; i >= 0 && count < 3; i--) {
            PatientRecord record = patientRecordList.get(i);
            if (record.getRecordType().equals(type)) {
                lastThree.add(record);
                count++; // Increment count for each matching record added
            }
        }

        // If fewer than 3 matching records were found, lastThree will have fewer than 3 elements
        return lastThree;
    }
}
