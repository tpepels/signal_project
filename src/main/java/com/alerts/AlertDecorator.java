package com.alerts;

public class AlertDecorator extends Alert {
    protected Alert decoratedAlert;

    public AlertDecorator(Alert decoratedAlert){
        super(decoratedAlert.getPatientId(), decoratedAlert.getCondition(), decoratedAlert.getTimestamp());
        this.decoratedAlert = decoratedAlert;
    }

    @Override
    public String getPatientId() {return decoratedAlert.getPatientId();}
    @Override
    public String getCondition() {
        return decoratedAlert.getCondition();
    }
    @Override
    public long getTimestamp() {
        return decoratedAlert.getTimestamp();
    }


}
