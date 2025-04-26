package com.alerts;

public class AlarmBloodSaturation implements IAlarm {
    static double comparisonMeasurment;
    static int count = 10; // adjusted for minutes for ease of debugging
    @Override
    public int[] evaluationFunction(double measurementValue) {
      int[] oneOrZeroAlert = new int[2];  
      //low blood saturation
      if (measurementValue < 0.92) {
        oneOrZeroAlert[0]=1;        
      }
      if (count == 10) {
        comparisonMeasurment = measurementValue;
      }
      if (count == 10) {
        count = 0;
      }
      // test for droping levels in 10 mins every time long number is for seconds
      if (comparisonMeasurment - measurementValue > 0.05) {
        count = count + 1;
        oneOrZeroAlert[1] = 1;                     
      }
      return oneOrZeroAlert;
    }
    
    
}
