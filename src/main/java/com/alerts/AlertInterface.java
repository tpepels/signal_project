package com.alerts;

public interface AlertInterface {
  String getPatientId();
  String getCondition();
  long  getTimestamp();
  String getMessage();


}
