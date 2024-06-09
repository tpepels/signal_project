package com.Main;

import com.cardio_generator.HealthDataSimulator;

public class HealthDataSimulatorInstance {

    private static HealthDataSimulator healthDataSimulator;

    public static HealthDataSimulator getInstance() {
        if (healthDataSimulator == null) {
            healthDataSimulator = new HealthDataSimulator();
        }
        return healthDataSimulator;
    }
}
