package com.cardio_generator.generators;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class PatientTest {
    private Patient patient;

    @Before
    public void setUp() {
        patient = new Patient("12345");
        patient.addRecord(new PatientRecord("12345", System.currentTimeMillis() - 1000, "HeartRate", 80));
        patient.addRecord(new PatientRecord("12345", System.currentTimeMillis() - 5000, "BloodPressure", 120));
    }

    @Test
    public void testGetRecords() {
        long now = System.currentTimeMillis();
        List<PatientRecord> records = patient.getRecords(now - 10000, now);
        assertEquals(2, records.size());
    }

    @Test
    public void testGetRecordsWithNoMatches() {
        long now = System.currentTimeMillis();
        List<PatientRecord> records = patient.getRecords(now - 20000, now - 10000);
        assertTrue(records.isEmpty());
    }
}

