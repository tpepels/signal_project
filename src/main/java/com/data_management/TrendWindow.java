package main.java.com.data_management;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.ArrayList;
import java.util.List;

public class TrendWindow {
    private Patient patient; 

    public TrendWindow(Patient patient){
        this.patient = patient; 
    }

    public List<Double> getWindowValues(long startTime, long endTime, String recordType){
        List<Double> windowValues = new ArrayList<>(); 
        List<PatientRecord> records = patient.getRecords(startTime, endTime); 
        for(PatientRecord record : records){
            if(record.getRecordType().equals(recordType)){
                windowValues.add(record.getMeasurementValue()); 
            }
        }
        return windowValues; 
    }
}
