package com.cardio_generator.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class that manages the storage of patient health records.
 * It allows adding records to a list, retrieving them, and printing all records for verification.
 */
public class DataStorage {
    // List to store patient records.
    private List<PatientRecord> records;

    /**
     * Constructor for DataStorage.
     * Initializes an ArrayList to hold the patient records.
     */
    public DataStorage() {
        this.records = new ArrayList<>();
    }

    /**
     * Adds a patient record to the storage.
     * @param record The PatientRecord object to be added to the storage.
     */
    public void addRecord(PatientRecord record) {
        // Add the records to the list        
        this.records.add(record);
    }

    /**
     * Retrieves all patient records from the storage.
     * @return A new list containing copies of all records, to prevent modification of the original list.
     */
    public List<PatientRecord> getRecords() {
        // Returns a copy of the list to avoid modifications
        return new ArrayList<>(this.records);
    }

    /**
     * Prints all patient records to the standard output.
     * This method is useful for debugging to verify that records are added correctly.
     */
    public void printAllRecords() {
        // Prints all the records to check if its added correctly
        for (PatientRecord record : records) {
            System.out.println(record.toString());
        }
    }

    
    public List<SaturationRecord> getSaturationRecordsForPatient(String patientId) {
        return records.stream()
                      .filter(r -> r instanceof SaturationRecord && r.getPatientId().equals(patientId))
                      .map(r -> (SaturationRecord) r)
                      .collect(Collectors.toList());
    }
    
    // Method to get blood pressure records for a specific patient
    public List<BloodPressureRecord> getBloodPressureRecordsForPatient(String patientId) {
        return records.stream()
                      .filter(r -> r instanceof BloodPressureRecord && r.getPatientId().equals(patientId))
                      .map(r -> (BloodPressureRecord) r)
                      .collect(Collectors.toList());
    }

    public List<ECGRecord> getECGRecordsForPatient(String patientId) {
        return records.stream()
                      .filter(r -> r instanceof ECGRecord && r.getPatientId().equals(patientId))
                      .map(r -> (ECGRecord) r)
                      .collect(Collectors.toList());
    }
    


}
