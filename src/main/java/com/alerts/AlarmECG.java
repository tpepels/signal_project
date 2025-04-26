package com.alerts;

public class AlarmECG implements IAlarm {

    static double[] window= new double[5];
    static int count = 1;
    static double sum;

    @Override
    public int[] evaluationFunction(double measurementValue) {
      int[] oneOrZeroAlert = new int[1];
      double average;
      if (count <= 5) {
        window[count % 5] = measurementValue;
        sum += measurementValue;     
      }
      if (count < 5){
        average = sum/count;
      }
      if (count > 5){
        average = sum/5;
      }
      if (average* 2.0 < measurementValue){
        oneOrZeroAlert[0] = 1;
      }
      if (count > 5){
        sum-= window[count % 5];
      }
      count = count + 1;
      return oneOrZeroAlert;
    }
    

}
