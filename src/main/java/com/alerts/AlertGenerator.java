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
        Integer patientId = new Integer(patient.getPatientId());
        if (records.size() >= 3) {
            // Check for increasing or decreasing trend
            PatientRecord record1 = records.get(records.size() - 3);
            PatientRecord record2 = records.get(records.size() - 2);
            PatientRecord record3 = records.get(records.size() - 1);

            //Do I have to get sort from reecords only those with systolic/diastolic and return last three?
            //Get last saturation level record.. etc (for combined alert)

            //Blood pressure alert

            //Check difference between blood pressures
            String measurmentValue = record1.getRecordType();
            switch(measurmentValue){
                case "SystolicPressure":
                    if(checkBloodPressure(record1,record2,record3, "Systolic")){
                        //First check combined alert
                        if(checkCombinedAlert(record1,record2,record3,"Systolic",patient)){
                            triggerAlert(new Alert(patientId.toString(), "Hypotensive Hypoxemia Alert", record3.getTimestamp()));
                        }

                        triggerAlert(new Alert(patientId.toString(), "Blood Pressure Alert", record3.getTimestamp()));
                    }
                    break;
                case "DiastolicPressure":
                    if(checkBloodPressure(record1,record2,record3, "Diastolic")){
                        //First check combined alert
                        if(checkCombinedAlert(record1,record2,record3,"Systolic",patient)){
                            triggerAlert(new Alert(patientId.toString(), "Hypotensive Hypoxemia Alert", record3.getTimestamp()));
                        }
                        triggerAlert(new Alert(patientId.toString(), "Blood Pressure Alert", record3.getTimestamp()));
                    }
                    break;
                case "Saturation":
                    if(checkBloodSaturation(record3,patient)){
                        triggerAlert(new Alert(patientId.toString(),"Blood Saturation Alert", record3.getTimestamp()));
                    }

            }


    }
    }

    public boolean checkCombinedAlert(PatientRecord record1, PatientRecord record2, PatientRecord record3, String type, Patient patient){
        if(checkBloodPressure(record1,record2,record3,type)&&checkBloodSaturation(record3, patient)){
            return true;
        }
        return false;
    }

    public boolean checkBloodSaturation(PatientRecord record3, Patient patient){
        if(record3.getMeasurementValue()<92){
            return true;
        }
        //Get the record
        List<PatientRecord>list = patient.getRecords(patient.getEndTime()-600000L, patient.getEndTime());
        //Check if there is a 5% difference between start and end measurment in 10 minutes
        if(Math.abs(list.get(0).getMeasurementValue()-list.get(list.size()-1).getMeasurementValue())>5){
            return true;
        }

        return false;

    }



    /**
     * Checks blood pressure condition to determine if alert should be triggered
     * @param record1
     * @param record2
     * @param record3
     * @param type
     * @return true if it should be triggered, otherwise false
     */
        public boolean checkBloodPressure(PatientRecord record1, PatientRecord record2, PatientRecord record3, String type){
        //Trend alert
            if(record2.getMeasurementValue()>=record1.getMeasurementValue()+10&&record3.getMeasurementValue()>=
                    record2.getMeasurementValue()+10){
                return true;
            }

            //Critical threshold alert
        if(type.equals("Systolic")){
            if(record3.getMeasurementValue()>180||record3.getMeasurementValue()<90){
                return true;
            }

        }else{
            if(record3.getMeasurementValue()>120||record3.getMeasurementValue()<60){
                return true;
            }
        }
        return false;

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
//        if(alert.getCondition().equals("Changed")){
//            String patientId = alert.getPatientId();
//            dataStorage.get
//        }

    }
}
