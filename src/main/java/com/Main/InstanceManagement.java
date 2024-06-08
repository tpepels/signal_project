package com.Main;

import com.cardio_generator.HealthDataSimulator;
import com.data_management.CholesterolReader;
import com.data_management.DataStorage;

@SuppressWarnings("unused")
public class InstanceManagement {

    private static DataStorage dataStorage;
    private static HealthDataSimulator healthDataSimulator;

    public static DataStorage getDatStorageInstance(CholesterolReader reader) {
        if (dataStorage == null) {
            dataStorage = new DataStorage(reader);
        }
        return dataStorage;
    }

    public static HealthDataSimulator getHealthDataSimulatorInstance() {
        if (healthDataSimulator == null) {
            healthDataSimulator = new HealthDataSimulator();
        }
        return healthDataSimulator;
    }
}
