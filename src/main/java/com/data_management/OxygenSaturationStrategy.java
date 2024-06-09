package com.data_management;

import com.alerts.Alert;
import com.alerts.BloodOxygenAlertFactory;

import java.util.List;
import java.util.stream.IntStream;

public class OxygenSaturationStrategy implements AlertStrategy {
    private Patient patient;
    private Long startTime;
    private Long endTime;
    private final String recordType = "Oxygen Saturation";

    public OxygenSaturationStrategy(Patient patient, Long startTime, Long endTime) {
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
    }



    @Override
    public void checkAlert(Patient patient, String recordType) {
        if (!recordType.equals("Oxygen Saturation")) {
            throw new IllegalArgumentException("Please provide the correct record");
        }
        for (PatientRecord record : patient.getRecords(startTime, endTime, recordType)) {
            if (record.getMeasurementValue() < 92) {
                new BloodOxygenAlertFactory();
            }

        }
    }

    @Override
    public void checkIntervals(Patient patient, String recordType) {
        if(!recordType.equals("Oxygen Saturation")){
            throw new IllegalArgumentException("Please provide the correct record");
        }
        List<PatientRecord> patientRec = patient.getRecords(startTime, endTime, recordType);
        IntStream.range(0, patientRec.size()).forEach(i -> {PatientRecord currentRecord = patientRec.get(i); long currentTime = currentRecord.getTimestamp(); double currentValue = currentRecord.getMeasurementValue();
        boolean alertTriggered = patientRec.stream().skip(i+1).takeWhile(nextRecord -> nextRecord.getTimestamp() <= currentTime + 10 * 60 * 1000).anyMatch(nextRecord -> nextRecord.getMeasurementValue() <= currentValue * 0.95);
        if(alertTriggered){PatientRecord alertRecord = patientRec.stream().skip(i+1).filter(nextRecord -> nextRecord.getMeasurementValue() <= currentValue * 0.95).findFirst().orElse(null);
        if(alertRecord != null){Alert alert = new BloodOxygenAlertFactory().createAlert(patient.getPatientId(), "Blood oxygen dropped 5% or more" , alertRecord.getTimestamp());}}
        });


    }
}
