package com.data_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alerts.AlertGenerator;

/**
 * Manages storage and retrieval of patient data within a healthcare monitoring
 * system.
 * This class serves as a repository for all patient records, organized by
 * patient IDs.
 */
public class DataStorage {
    private Map<Integer, Patient> patientMap; // Stores patient objects indexed by their unique patient ID.

    /**
     * Constructs a new instance of DataStorage, initializing the underlying storage
     * structure.
     */
    public DataStorage() {
        this.patientMap = new HashMap<>();
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
        Patient patient = patientMap.get(patientId);
        if (patient == null) {
            patient = new Patient(patientId);
            patientMap.put(patientId, patient);
        }
        patient.addRecord(measurementValue, recordType, timestamp);
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
    public List<com.data_management.PatientRecord> getRecords(int patientId, long startTime, long endTime) {
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
     * Initializes the system, reads data into storage, and continuously monitors
     * and evaluates patient data.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // DataReader is not defined in this scope, should be initialized appropriately.
        // DataReader reader = new SomeDataReaderImplementation("path/to/data");
        DataStorage storage = new DataStorage();

        // Assuming the reader has been properly initialized and can read data into the
        // storage
        // reader.readData(storage);

        // Initialize the AlertGenerator with the storage
        AlertGenerator alertGenerator = new AlertGenerator(storage);

        // Evaluate all patients' data to check for conditions that may trigger alerts
        for (Patient patient : storage.getAllPatients()) {
            alertGenerator.evaluateData(patient);
        }
    }


    // Almacena los registros de pacientes, donde la clave es el identificador del paciente
    private Map<String, PatientRecord> patientRecords = new HashMap<>();

    // Clase interna para representar el registro de un paciente
    private static class PatientRecord {
        // Agrega aquí los campos necesarios, por ejemplo:
        private String id;
        private String name;
        private List<Data> dataPoints;

        public PatientRecord(String id, String name) {
            this.id = id;
            this.name = name;
            this.dataPoints = new ArrayList<>();
        }

        public synchronized void addData(Data data) {
            // Lógica para actualizar el registro del paciente con nuevos datos
            this.dataPoints.add(data);
        }

        public synchronized void update(Data data) {
            // Lógica para actualizar campos específicos del registro del paciente
            // Actualiza los campos según los datos entrantes
        }
    }

    // Método para almacenar datos de pacientes sin duplicar información
    public synchronized void store(Data data) {
        String patientId = data.getPatientId(); // Obtén el ID del paciente de los datos entrantes
        String patientName = data.getPatientName(); // Obtén el nombre del paciente de los datos entrantes

        if (patientRecords.containsKey(patientId)) {
            // Si el registro del paciente ya existe, actualiza el registro existente
            PatientRecord existingRecord = patientRecords.get(patientId);
            existingRecord.update(data);
        } else {
            // Si el registro del paciente no existe, crea un nuevo registro
            PatientRecord newRecord = new PatientRecord(patientId, patientName);
            newRecord.addData(data);
            patientRecords.put(patientId, newRecord);
        }
    }

    // Clase Data (ejemplo, deberías ajustarla según tu implementación real)
    public static class Data {
        private String patientId;
        private String patientName;
        private String healthMetric;
        private String value;

        // Constructor y getters
        public Data(String patientId, String patientName, String healthMetric, String value) {
            this.patientId = patientId;
            this.patientName = patientName;
            this.healthMetric = healthMetric;
            this.value = value;
        }

        public String getPatientId() {
            return patientId;
        }

        public String getPatientName() {
            return patientName;
        }

        public String getHealthMetric() {
            return healthMetric;
        }

        public String getValue() {
            return value;
        }
    
    }

}
