package com.cardio_generator.generators;

public class HealthDataGenerator implements IAlarmManual {

    @Override
    public boolean manualAlarm(int oneOrZero) {
      if (oneOrZero == 1) {
        return true;
      } else {
          return false;
      }
    }
        

}
