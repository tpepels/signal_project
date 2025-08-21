package com.data_management;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.alerts.AlertFactory;
import com.alerts.AlertGenerator;

/**
 * Manages storage and retrieval of patient data within a healthcare monitoring
 * system.
 * This class serves as a repository for all patient records, organized by
 * patient IDs.
 */
public class DataStorage {

    // Singleton instance
    private static volatile DataStorage instance;
    private final ConcurrentMap<Integer, Patient> patientMap;

    /**
     * Constructs a new instance of DataStorage, initializing the underlying storage
     * structure.
     */
    protected DataStorage() {
        this.patientMap = new ConcurrentHashMap<>();
    }

    /**
     * Returns the singleton instance of DataStorage.
     */
    public static DataStorage getInstance() {
        if (instance == null) {
            synchronized (DataStorage.class) {
                if (instance == null) {
                    instance = new DataStorage();
                }
            }
        }
        return instance;
    }

    /**
     * Adds or updates patient data in the storage.
     * If the patient does not exist, a new Patient object is created and added to
     * the storage.
     * Otherwise, the new data is added to the existing patient's records.
     *
     * @param patientId        the unique identifier of the patient
     * @param measurementValue the value of the health metric being recorded
     * @param recordType       the type of record, e.g., "HeartRate",
     *                         "BloodPressure"
     * @param timestamp        the time at which the measurement was taken, in
     *                         milliseconds since the Unix epoch
     */
    public void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
        patientMap
                .computeIfAbsent(patientId, id -> new Patient(id))
                .addRecord(measurementValue, recordType, timestamp);
    }

    /**
     * Retrieves a list of PatientRecord objects for a specific patient, filtered by
     * a time range.
     *
     * @param patientId the unique identifier of the patient whose records are to be
     *                  retrieved
     * @param startTime the start of the time range, in milliseconds since the Unix
     *                  epoch
     * @param endTime   the end of the time range, in milliseconds since the Unix
     *                  epoch
     * @return a list of PatientRecord objects that fall within the specified time
     *         range
     */
    public List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        if (patient != null) {
            return patient.getRecords(startTime, endTime);
        }
        return new ArrayList<>(); // return an empty list if no patient is found
    }

    /**
     * Retrieves a collection of all patients stored in the data storage.
     *
     * @return a list of all patients
     */
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    /**
     * The main method for the DataStorage class.
     * Demonstrates adding data, retrieving records, and evaluating alerts.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        DataStorage storage = DataStorage.getInstance();

        // Add some sample data
        storage.addPatientData(1, 80.0, "HeartRate", 1700000000001L);
        storage.addPatientData(1, 120.0, "BloodPressure", 1700000001000L);
        storage.addPatientData(2, 75.0, "HeartRate", 1700000002000L);

        // Example of using DataStorage to retrieve and print records for a patient
        List<PatientRecord> records = storage.getRecords(1, 1700000000000L, 1800000000000L);
        for (PatientRecord record : records) {
            System.out.println("Record for Patient ID: " + record.getPatientId() +
                    ", Type: " + record.getRecordType() +
                    ", Data: " + record.getMeasurementValue() +
                    ", Timestamp: " + record.getTimestamp());
        }

        // Initialize the AlertGenerator with the storage (using empty strategies and alerts for demo)
        AlertGenerator alertGenerator = new AlertGenerator(storage, new ArrayList<>(), new ArrayList<>());

        // Evaluate all patients' data to check for conditions that may trigger alerts
        for (Patient patient : storage.getAllPatients()) {
            alertGenerator.evaluateData(patient);
        }
    }

    public void clear() {
        patientMap.clear();
    }
}

