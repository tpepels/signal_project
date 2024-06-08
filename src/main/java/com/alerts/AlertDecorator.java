package com.alerts;

public class AlertDecorator extends Alert {
    private String patientId;
    private String condition;
    private long timestamp;

    public AlertDecorator(String patientId, String condition, long timeStamp){
        super(patientId, condition, timeStamp);
    }

    @Override
    public String getPatientId() {return patientId;}
    @Override
    public String getCondition() {
        return condition;
    }
    @Override
    public long getTimestamp() {
        return timestamp;
    }

}
