package com.data_management;

/**
 * Represents a single record of patient data at a specific point in time.
 * This class stores all necessary details for a single observation or
 * measurement
 * taken from a patient, including the type of record (such as ECG, blood
 * pressure),
 * the measurement value, and the exact timestamp when the measurement was
 * taken.
 */

import java.util.ArrayList;
import java.util.List;

class PatientRecord {
    private int patientId;
    private String recordType; 
    private double measurementValue; 
    private long timestamp;

    public PatientRecord(int patientId, double measurementValue, String recordType, long timestamp) {
        this.patientId = patientId;
        this.measurementValue = measurementValue;
        this.recordType = recordType;
        this.timestamp = timestamp;
    }

    public int getPatientId() {
        return patientId;
    }

    public double getMeasurementValue() {
        return measurementValue;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getRecordType() {
        return recordType;
    }
}

class DataStorage {
    private List<Patient> patients;

    public DataStorage() {
        this.patients = new ArrayList<>();
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public List<Patient> getAllPatients() {
        return patients;
    }
}

class Patient {
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

    public char[] getPatientId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPatientId'");
    }
}

class AlertGenerator {
    private DataStorage dataStorage;

    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public void evaluateData(Patient patient) {
        List<PatientRecord> records = patient.getRecords(System.currentTimeMillis() - 86400000, System.currentTimeMillis()); 
        for (PatientRecord record : records) {
            if ("BloodSaturation".equals(record.getRecordType())) {
                double saturation = record.getMeasurementValue();
                if (saturation < 92.0) {
                    triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Low saturation", record.getTimestamp()));
                }
            }
        }
    }

    private void triggerAlert(Alert alert) {
        System.out.println("Alert Triggered: Patient ID = " + alert.getPatientId() +
                ", Condition = " + alert.getCondition() +
                ", Timestamp = " + alert.getTimestamp());
    }
}

class Alert {
    private String patientId;
    private String condition;
    private long timestamp;

    public Alert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getCondition() {
        return condition;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

public class BloodSaturationRecord extends PatientRecord {
    public BloodSaturationRecord(int patientId, double measurementValue, long timestamp) {
        super(patientId, measurementValue, "BloodSaturation", timestamp);
    }
}

