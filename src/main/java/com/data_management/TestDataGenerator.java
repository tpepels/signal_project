package com.data_management;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TestDataGenerator {
    private static final Random random = new Random();

    public static List<PatientRecord> generateBloodPressureData(int patientId, int numRecords) {
        List<PatientRecord> records = new ArrayList<>();
        long currentTime = System.currentTimeMillis();

        for (int i = 0; i < numRecords; i++) {
            double systolic = 90 + random.nextInt(101);  // Random value between 90 and 190
            double diastolic = 60 + random.nextInt(61);  // Random value between 60 and 120
            long timestamp = currentTime - (numRecords - i) * 60000L;  // Decrease timestamp by 1 minute for each record
            records.add(new PatientRecord(patientId, systolic, "SystolicBloodPressure", timestamp));
            records.add(new PatientRecord(patientId, diastolic, "DiastolicBloodPressure", timestamp));
        }

        return records;
    }

    public static List<PatientRecord> generateBloodOxygenData(int patientId, int numRecords) {
        List<PatientRecord> records = new ArrayList<>();
        long currentTime = System.currentTimeMillis();

        for (int i = 0; i < numRecords; i++) {
            double saturation = 85 + random.nextInt(16);  // Random value between 85 and 100
            long timestamp = currentTime - (numRecords - i) * 60000L;  // Decrease timestamp by 1 minute for each record
            records.add(new PatientRecord(patientId, saturation, "BloodOxygenSaturation", timestamp));
        }

        return records;
    }
}
