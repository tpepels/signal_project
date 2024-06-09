package com.cardio_generator.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MockDataStorage extends DataStorage {
    private List<PatientRecord> records;

    public MockDataStorage() {
        records = new ArrayList<>();
    }

    public void addMockRecord(PatientRecord record) {
        records.add(record);
    }

    @Override
    public List<PatientRecord> getRecordsForPatient(String patientId) {
        // Filter records by patientId if necessary
        return records;
    }
}