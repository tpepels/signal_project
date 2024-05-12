package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
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
        Integer patientId = new Integer(patient.getPatientId());



                //Check Blood Pressure

                List<PatientRecord>listOfLastThreeSist = getLastThreeBloodPressure(patient,"Systolic");
                if(listOfLastThreeSist.size()>=3){
                    PatientRecord record1Syst = listOfLastThreeSist.get(listOfLastThreeSist.size() - 3);
                    PatientRecord record2Syst = listOfLastThreeSist.get(listOfLastThreeSist.size() - 2);
                    PatientRecord record3Syst = listOfLastThreeSist.get(listOfLastThreeSist.size() - 1);
                    //Check systolic
                    if(checkBloodPressure(record1Syst,record2Syst,record3Syst,"Systolic")){
                        triggerAlert(new Alert(patientId.toString(), "Blood Pressure Alert", record3Syst.getTimestamp()));
                    }
                }



                //Same for diastolic
                List<PatientRecord>listOfLastThreeDiast = getLastThreeBloodPressure(patient,"Diastolic");
                if(listOfLastThreeDiast.size()>=3){
                    PatientRecord record1Diast = listOfLastThreeDiast.get(listOfLastThreeDiast.size() - 3);
                    PatientRecord record2Diast = listOfLastThreeDiast.get(listOfLastThreeDiast.size() - 2);
                    PatientRecord record3Diast = listOfLastThreeDiast.get(listOfLastThreeDiast.size() - 1);
                    if(checkBloodPressure(record1Diast,record2Diast,record3Diast,"Diastolic")){
                        triggerAlert(new Alert(patientId.toString(), "Blood Pressure Alert", record3Diast.getTimestamp()));
                    }

                }


                //Check Blood Saturation
                List<PatientRecord>lastTenMinutes = patient.lastTenMinutesOfType("Saturation");
                if(lastTenMinutes.size()>=1){
                    if(checkBloodSaturation(lastTenMinutes.get(0),lastTenMinutes.get(lastTenMinutes.size()-1),patient)){
                        triggerAlert(new Alert(patientId.toString(), "Blood Pressure Alert", lastTenMinutes.get(lastTenMinutes.size()-1).getTimestamp()));
                    }
                }

                //Check combined Alert
        if(listOfLastThreeSist.size()>=3){
            PatientRecord record3Syst = listOfLastThreeSist.get(listOfLastThreeSist.size() - 1);
            if(checkCombinedAlert(record3Syst,lastTenMinutes.get(lastTenMinutes.size()-1))){
                List<PatientRecord>recordsForTimeRetrieving = patient.getRecords(patient.getStartTime(),patient.getEndTime());
                triggerAlert(new Alert(patientId.toString(),"Hypotensive Hypoxemia Alert",recordsForTimeRetrieving.get(recordsForTimeRetrieving.size()-1).getTimestamp()));
            }
        }

                //ECG Alert
                PatientRecord lastECGRecord = patient.getECGRecord();
                List<PatientRecord>lastTenMinutesECG = patient.lastTenMinutesOfType("ECG");
                if(lastTenMinutesECG.size()!=0){
                    if(checkECGALert(lastECGRecord,lastTenMinutesECG)){
                        triggerAlert(new Alert(patientId.toString(),"ECG Data Alert",lastECGRecord.getTimestamp()));
                    }
                }







    }

    public boolean checkECGALert(PatientRecord lastECGRecord,List<PatientRecord>lastTenMinutesECG){
        if(lastECGRecord.getMeasurementValue()<50||lastECGRecord.getMeasurementValue()>100){
            return true;
        }
        for(int i = lastTenMinutesECG.size()-1;i>0;i--){
            //Check for irregular beat
            if(Math.abs(lastTenMinutesECG.get(i).getMeasurementValue()-lastTenMinutesECG.get(i-1).getMeasurementValue())>20){
                return true;
            }
        }
        return false;
    }
    public boolean checkCombinedAlert(PatientRecord record3Syst,PatientRecord record3Saturation){
        if(record3Syst.getMeasurementValue()<90&&record3Saturation.getMeasurementValue()<92){
            return true;
        }
        return false;
    }

    public boolean checkBloodSaturation(PatientRecord recordFirst, PatientRecord record3, Patient patient){
        if(record3.getMeasurementValue()<92){
            return true;
        }
        //Check if there is a 5% difference between start and end measurment in 10 minutes
        if(Math.abs(recordFirst.getMeasurementValue()-record3.getMeasurementValue())>5){
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

    public List<PatientRecord> getLastThreeBloodPressure(Patient patient, String type) {
        List<PatientRecord> patientRecordList = patient.getRecords(patient.getStartTime(), patient.getEndTime());
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
        System.out.println("Alert was triggered. Details:");
        System.out.println(alert.getCondition());
        System.out.println(alert.getPatientId());
    }
}
