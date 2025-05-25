package com.data_management;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton version of the DataStorage class that manages patient records.
 */
public class DataStorageSingleton {
    private static DataStorageSingleton instance;
    private Map<Integer, Patient> patientMap;

  
    private DataStorageSingleton() {
        this.patientMap = new ConcurrentHashMap<>();
    }

   
    public static synchronized DataStorageSingleton getInstance() {
        if (instance == null) {
            instance = new DataStorageSingleton();
        }
        return instance;
    }

    /**
     * Adds or updates patient data in the storage.
     *
     * @param patientId        the unique identifier of the patient
     * @param measurementValue the value of the health metric being recorded
     * @param recordType       the type of record, e.g., "HeartRate", "BloodPressure"
     * @param timestamp        the time at which the measurement was taken, in milliseconds since the Unix epoch
     */
    public void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
       
        patientMap.compute(patientId, (id, patient) -> {
            if (patient == null) {
                patient = new Patient(patientId);
            }
            synchronized (patient) {
               
                boolean duplicate = patient.getRecords(timestamp, timestamp).stream().anyMatch(r -> r.getRecordType().equals(recordType));

                if (!duplicate) {
                    patient.addRecord(measurementValue, recordType, timestamp);
                }
            }
            return patient;
        });
    }

    /**
     * Retrieves a list of PatientRecord objects for a specific patient, filtered by a time range.
     *
     * @param patientId the unique identifier of the patient
     * @param startTime the start of the time range
     * @param endTime   the end of the time range
     * @return a list of records within the specified range
     */
    public List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        if (patient != null) {
            synchronized (patient) {
                return patient.getRecords(startTime, endTime);
            }
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves all patients in the system.
     *
     * @return a list of all stored patients
     */
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    public void clear() {
        patientMap.clear();
    }

}
