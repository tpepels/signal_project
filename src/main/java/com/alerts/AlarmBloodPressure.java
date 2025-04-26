package com.alerts;

public class AlarmBloodPressure implements IAlarm {

    @Override
    public int[] evaluationFunction(double measurementValue) {
      int[] oneOrZeroAlert= new int[4];
      
      if (measurementValue > 120 || measurementValue < 60) { //diastolic
        oneOrZeroAlert[0] = 1;
      }
      if (measurementValue > 180 || measurementValue < 90) { // systolic
        oneOrZeroAlert[1] = 1;
      } 
      // for when 3 measurements are ten difference to minus or plus in a row
      else {
        int countTenTimesDeviationPlus = 0;
        int countTenTimesDeviationMinus = 0;
        double previousNumber = measurementValue;

        if (previousNumber - measurementValue > 10) {
          countTenTimesDeviationPlus = countTenTimesDeviationPlus + 1;
        }
        if (previousNumber - measurementValue < -10) {
          countTenTimesDeviationMinus = countTenTimesDeviationMinus + 1;
        }
        if (Math.abs(previousNumber - measurementValue) <= 10) {
          countTenTimesDeviationMinus = 0;
          countTenTimesDeviationPlus = 0;
        }
        if (countTenTimesDeviationMinus == 3) {
          oneOrZeroAlert[3] = 1;
        }
        if (countTenTimesDeviationPlus == 3) {
          oneOrZeroAlert[2] = 1;

        }
      }
      return oneOrZeroAlert;
    }

}
