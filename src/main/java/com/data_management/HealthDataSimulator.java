package com.data_management;
public class DataStorage {
    private static DataStorage instance;

    private DataStorage() {
        // private constructor to prevent instantiation
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    // Data storage methods
}

public class HealthDataSimulator {
    private static HealthDataSimulator instance;

    private HealthDataSimulator() {
        // private constructor to prevent instantiation
    }

    public static HealthDataSimulator getInstance() {
        if (instance == null) {
            instance = new HealthDataSimulator();
        }
        return instance;
    }

    // Simulation methods
}
