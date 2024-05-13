package com.cardio_generator.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alerts.Alert;
import com.cardio_generator.outputs.OutputStrategy;

public class AlertGenerator implements PatientDataGenerator {

    private DataStorage dataStorage;

    // Constructor for connecting to the DataStorage
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    // Evaluates data for a single patient within the last 24 hours
    public void evaluateData(Patient patient) {
        evaluateBloodPressureRecords(patient);
        evaluateSaturationRecords(patient);
        evaluateCombinedAlerts(patient);
    }
    

    // Process each record to check for conditions that might trigger alerts
    private void processRecordForAlerts(Patient patient, PatientRecord record) {
        if ("HeartRate".equals(record.getRecordType()) && record.getMeasurementValue() > 100) {
            triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "High heart rate", record.getTimestamp()));
        } else if ("BloodPressure".equals(record.getRecordType())) {
            BloodPressureRecord bpRecord = (BloodPressureRecord) record;
            if (checkTrendAlert(bpRecord)) {
                triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Trend Alert: Blood Pressure Trend Detected", record.getTimestamp()));
            }
            if (checkCriticalThresholdAlert(bpRecord)) {
                triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Critical Threshold Alert: Abnormal Blood Pressure Detected", record.getTimestamp()));
            }
        }
    }

    // Trigger an alert with a specific message
    private void triggerAlert(Alert alert) {
        System.out.println("Alert Triggered: Patient ID = " + alert.getPatientId() +
                           ", Condition = " + alert.getCondition() +
                           ", Timestamp = " + alert.getTimestamp());
    }

    // Logic to check for a trend in blood pressure measurements indicating a consistent rise or drop
    private boolean checkTrendAlert(BloodPressureRecord record) {
        // This method should compare consecutive readings, but here's a placeholder for simplicity
        return false;
    }

    // Logic to check if blood pressure measurements exceed critical thresholds
    private boolean checkCriticalThresholdAlert(BloodPressureRecord record) {
        if (record.getSystolicPressure() > 180 || record.getSystolicPressure() < 90 || 
            record.getDiastolicPressure() > 120 || record.getDiastolicPressure() < 60) {
            return true;
        }
        return false;
    }

    // Constants should be written in CONSTANT_CASE
    private static final Random RANDOM_GENERATOR = new Random();
    // Use lowerCamelCase for instance variables
    private boolean[] alertStates; // False = resolved, true = pressed

    // Additional constructor for managing alert states
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generate'");
    }

    /**
     * Checks patient records against specific health criteria and triggers alerts if criteria are met.
     * @param patient The patient whose records are being checked.
     * @param record A single record of patient data.
     */
    private void checkAndTriggerAlerts(Patient patient, PatientRecord record) {
        // Example: Trigger an alert if heart rate is abnormally high
        if ("HeartRate".equals(record.getRecordType()) && record.getMeasurementValue() > 100) {
            triggerAlert(new Alert(patient.getPatientId(), "High heart rate detected", record.getTimestamp()));
        }

        // Example: Trigger alerts for critical blood pressure values
        if ("BloodPressure".equals(record.getRecordType())) {
            BloodPressureRecord bpRecord = (BloodPressureRecord) record;
            if (bpRecord.getSystolicPressure() > 180 || bpRecord.getDiastolicPressure() < 60) {
                triggerAlert(new Alert(patient.getPatientId(), "Critical blood pressure level detected", record.getTimestamp()));
            }
        }
    }

    private void checkBloodPressureAlerts(Patient patient, List<PatientRecord> records) {
        checkTrendAlert(patient, records);
        checkCriticalThresholdAlert(patient, records);
    }

    private void checkTrendAlert(Patient patient, List<PatientRecord> records) {
        if (records.size() < 3) return; // Need at least three records to check trend

        for (int i = 2; i < records.size(); i++) {
            BloodPressureRecord previous = (BloodPressureRecord) records.get(i-2);
            BloodPressureRecord middle = (BloodPressureRecord) records.get(i-1);
            BloodPressureRecord current = (BloodPressureRecord) records.get(i);

            if (isTrending(previous.getSystolicPressure(), middle.getSystolicPressure(), current.getSystolicPressure()) ||
                isTrending(previous.getDiastolicPressure(), middle.getDiastolicPressure(), current.getDiastolicPressure())) {
                triggerAlert(new Alert(patient.getPatientId(), "Trend Alert: Blood Pressure Trend Detected", current.getTimestamp()));
                break;
            }
        }
    }

    private boolean isTrending(int first, int second, int third) {
        return (second - first > 10 && third - second > 10) || (first - second > 10 && second - third > 10);
    }

    private void checkCriticalThresholdAlert(Patient patient, List<PatientRecord> records) {
        for (PatientRecord record : records) {
            BloodPressureRecord bpRecord = (BloodPressureRecord) record;
            if (bpRecord.getSystolicPressure() > 180 || bpRecord.getSystolicPressure() < 90 ||
                bpRecord.getDiastolicPressure() > 120 || bpRecord.getDiastolicPressure() < 60) {
                triggerAlert(new Alert(patient.getPatientId(), "Critical Threshold Alert: Abnormal Blood Pressure Detected", bpRecord.getTimestamp()));
            }
        }
    }

    private List<String> alerts;  // To store alert messages for verification in tests
        
    public List<String> getAlerts() {
        return alerts;
    }

    private void evaluateSaturationRecords(Patient patient) {
        List<SaturationRecord> records = dataStorage.getSaturationRecordsForPatient(patient.getPatientId());
        SaturationRecord previousRecord = null;
    
        for (SaturationRecord record : records) {
            if (record.getSaturation() < 92) {
                triggerAlert(new Alert(patient.getPatientId(), "Low Saturation Alert", record.getTimestamp()));
            }
    
            if (previousRecord != null && (record.getTimestamp() - previousRecord.getTimestamp() <= 600000)) { // 600000 ms = 10 minutes
                if (previousRecord.getSaturation() - record.getSaturation() >= 5) {
                    triggerAlert(new Alert(patient.getPatientId(), "Rapid Drop in Saturation Alert", record.getTimestamp()));
                }
            }
            previousRecord = record;
        }
    }


    private void evaluateCombinedAlerts(Patient patient) {
        List<BloodPressureRecord> bpRecords = dataStorage.getBloodPressureRecordsForPatient(patient.getPatientId());
        List<SaturationRecord> saturationRecords = dataStorage.getSaturationRecordsForPatient(patient.getPatientId());
    
        // Assuming records are sorted by timestamp and timestamps are aligned; consider synchronization of records if not
        for (BloodPressureRecord bpRecord : bpRecords) {
            SaturationRecord correspondingSaturationRecord = findCorrespondingSaturationRecord(saturationRecords, bpRecord.getTimestamp());
            if (correspondingSaturationRecord != null &&
                bpRecord.getSystolicPressure() < 90 &&
                correspondingSaturationRecord.getSaturation() < 92) {
                triggerAlert(new Alert(patient.getPatientId(), "Hypotensive Hypoxemia Alert", bpRecord.getTimestamp()));
                break; // Assuming one alert per evaluation is sufficient
            }
        }
    }
    
    private SaturationRecord findCorrespondingSaturationRecord(List<SaturationRecord> saturationRecords, long timestamp) {
        // Find the closest matching timestamp, assuming records are in chronological order
        for (SaturationRecord record : saturationRecords) {
            if (record.getTimestamp() == timestamp) {  // This could be adjusted to a range if exact matches are unlikely
                return record;
            }
        }
        return null;
    }


    private void evaluateECGRecords(Patient patient) {
        List<ECGRecord> ecgRecords = dataStorage.getECGRecordsForPatient(patient.getPatientId());
    
        for (ECGRecord record : ecgRecords) {
            if (record.getHeartRate() < 50 || record.getHeartRate() > 100) {
                triggerAlert(new Alert(patient.getPatientId(), "Abnormal Heart Rate Alert", record.getTimestamp()));
            }
    
            if (isIrregularBeat(record.getBeatIntervals())) {
                triggerAlert(new Alert(patient.getPatientId(), "Irregular Beat Alert", record.getTimestamp()));
            }
        }
    }
    
    private boolean isIrregularBeat(List<Double> beatIntervals) {
        // Simple example: Check for significant variation between beats
        double previousInterval = -1;
        for (double interval : beatIntervals) {
            if (previousInterval != -1 && Math.abs(interval - previousInterval) > 0.1) { // Threshold is example
                return true;
            }
            previousInterval = interval;
        }
        return false;
    }
       
    public AlertGenerator() {
        this.alerts = new ArrayList<>();
    }
      
}


