package com.alerts.alerts;


public abstract class Alert {
    int patientId;
    String condition;
    long timestamp;

    public Alert(int patientId, String condition, long timestamp) {
    this.patientId = patientId;
    this.condition = condition;
    this.timestamp = timestamp;
    }

    public abstract String toString();

    public int getPatientId()
    {
        return patientId;
    }

    public String getCondition()
    {
        return condition;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setCondition(String condition){
        this.condition = condition;
    }

}