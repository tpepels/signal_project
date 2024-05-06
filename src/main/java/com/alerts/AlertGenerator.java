package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        // Implementation goes here
        //get last record
        List<PatientRecord> records = patient.getRecords(patient.getPrevThreeTime(), patient.getEndTime());

        if (records.size() >= 3) {
            // Check for increasing or decreasing trend
            PatientRecord record1 = records.get(records.size() - 3);
            PatientRecord record2 = records.get(records.size() - 2);
            PatientRecord record3 = records.get(records.size() - 1);

            // Calculate difference between consecutive readings
            double diff1 = record2.getMeasurementValue() - record1.getMeasurementValue();
            double diff2 = record3.getMeasurementValue() - record2.getMeasurementValue();

            if (Math.abs(diff1) > 10 && Math.abs(diff2) > 10) {
                triggerAlert(new Alert(record1.getPatientId(), "Blood Pressure Trend Detected", System.currentTimeMillis()));
            }
        }

        // Check for critical threshold alerts
        PatientRecord latestRecord = records.get(records.size() - 1);
        double value = latestRecord.getMeasurementValue();
        String recordType = latestRecord.getRecordType();

        if ((recordType.equalsIgnoreCase("Systolic") && (value > 180 || value < 90)) ||
                (recordType.equalsIgnoreCase("Diastolic") && (value > 120 || value < 60))) {
            triggerAlert(new Alert(patient.getPatientId(), "Critical Blood Pressure", System.currentTimeMillis()));
        }
    }


    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
        if(alert.getCondition().equals("Changed")){
            String patientId = alert.getPatientId();
            dataStorage.get
        }

    }
}
