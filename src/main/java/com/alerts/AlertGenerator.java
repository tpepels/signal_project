package com.alerts;

import java.util.ArrayList;
import java.util.List;

import com.cardio_generator.generators.HealthDataGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

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
        //list all conditions with if statements
         IAlarm alarmReference;
         IAlarmManual alarmManual;
         List<PatientRecord> records = new ArrayList<>();
         // create patient record to get all info over all times that have passed
         records=patient.getRecords(0000000000000L, 9999999999999L);
         // only use data of last parts of records because those are most relevant and informative (otherwise you are allready to late :( ))
        for (int i = 0; records.size() < i; i++) {
           
           if (records.get(i).getRecordType().equals("BloodPressure")) {
             alarmReference = new AlarmBloodPressure();
             int[] result = alarmReference.evaluationFunction(records.get(i).getMeasurementValue());
             if(result[1]==1){
                Alert alert= new Alert(Integer.toString(records.get(i).getPatientId()),"Systolic",records.get(i).getTimestamp());
                triggerAlert(alert);
             }
             if(result[0]==1){
                Alert alert= new Alert(Integer.toString(records.get(i).getPatientId()),"diastolic",records.get(i).getTimestamp());
                triggerAlert(alert);
             }
             if(result[2]==1){
                Alert alert= new Alert(Integer.toString(records.get(i).getPatientId()),"3 times in a row 10 mmg higer",records.get(i).getTimestamp());
                triggerAlert(alert);
             }
             if(result[3]==1){
                Alert alert= new Alert(Integer.toString(records.get(i).getPatientId()),"3 times in a row 10 mmg lower",records.get(i).getTimestamp());
                triggerAlert(alert);
             }
           }        
           if (records.get(i).getRecordType().equals("BloodSaturation")) {
            // test for low saturation
            alarmReference = new AlarmBloodSaturation();
            int[] result = alarmReference.evaluationFunction(records.get(i).getMeasurementValue());
            if (result[0]==1) {
               Alert alert= new Alert(Integer.toString(records.get(i).getPatientId()),"Low blood saturation",records.get(i).getTimestamp());
               triggerAlert(alert);
            }
            if (result[1]==1) {
               Alert alert= new Alert(Integer.toString(records.get(i).getPatientId()),"10 minutes or longer deviation of 5%",records.get(i).getTimestamp());
               triggerAlert(alert);
            }

           }
           if (records.get(i).getRecordType().equals("CombinedAlert")) {
             alarmReference = new AlarmBloodSaturation();
             int[] resultS = alarmReference.evaluationFunction(records.get(i).getMeasurementValue());
             alarmReference = new AlarmBloodPressure();
             int[] resultP = alarmReference.evaluationFunction(records.get(i).getMeasurementValue());

             if (resultS[0]==1 && resultP[1]==1){
               Alert alert= new Alert(Integer.toString(records.get(i).getPatientId()),"Hypotensive Hypoxemia Alert",records.get(i).getTimestamp());
               triggerAlert(alert); 
             }
                
                
           }
           if (records.get(i).getRecordType().equals("ECG")) {
             // test for low saturation
             alarmReference = new AlarmECG();
             int[] result = alarmReference.evaluationFunction(records.get(i).getMeasurementValue());
             if (result[0]==1) {
               Alert alert= new Alert(Integer.toString(records.get(i).getPatientId()),"ECG big difference",records.get(i).getTimestamp());
               triggerAlert(alert);
             }                    
           }
           else {
            // test for low saturation
            alarmManual = new HealthDataGenerator();
            if (alarmManual.manualAlarm(i)) {
              Alert alert= new Alert(Integer.toString(records.get(i).getPatientId()),"ECG big difference",records.get(i).getTimestamp());
              triggerAlert(alert);
            }                    
          }
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
    }
}
