package com.alerts;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private static DataStorage instance;
    private final Map<Integer, Map<String, Double>> data = new HashMap<>();

    private DataStorage() {}

    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public void store(String patientId, String metricType, double value) {
        data.computeIfAbsent(Integer.parseInt(patientId), k -> new HashMap<>()).put(metricType, value);
    }

    public Map<String, Double> getPatientData(int patientId) {
        return data.getOrDefault(patientId, new HashMap<>());
    }
}
