package data_management;

import com.alerts.Alert;
import com.alerts.LowSaturationAlert;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class LowSaturationAlertTest {
    @Test
    public void testSaturationExactlyAtThreshold() {
        Patient patient = new Patient(1);
        patient.addRecord(92, "Saturation", System.currentTimeMillis());
        LowSaturationAlert alert = new LowSaturationAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertTrue(alerts.isEmpty(), "No alert should be generated for saturation exactly at 92%");
    }

    @Test
    public void testSaturationJustAboveThreshold() {
        Patient patient = new Patient(1);
        patient.addRecord(93, "Saturation", System.currentTimeMillis());
        LowSaturationAlert alert = new LowSaturationAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertTrue(alerts.isEmpty(), "No alert should be generated for saturation above 92%");
    }

    @Test
    public void testMultipleRecords() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(95, "Saturation", now - 10000);
        patient.addRecord(90, "Saturation", now);
        patient.addRecord(91, "Saturation", now + 10000);

        LowSaturationAlert alert = new LowSaturationAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(2, alerts.size(), "Alerts should be generated for each record below 92%");
    }

    @Test
    public void testNoRecords() {
        Patient patient = new Patient(1);
        LowSaturationAlert alert = new LowSaturationAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertTrue(alerts.isEmpty(), "No alerts should be generated when no records are present");
    }

    @Test
    public void testRapidSequenceOfLowRecords() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(88, "Saturation", now);
        patient.addRecord(85, "Saturation", now + 5000);
        patient.addRecord(86, "Saturation", now + 10000);

        LowSaturationAlert alert = new LowSaturationAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(3, alerts.size(), "An alert should be generated for each low record in rapid sequence");
    }
}