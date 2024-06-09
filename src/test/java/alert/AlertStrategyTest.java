package alert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.alerts.AbnormalDataAlert;
import com.alerts.Alert;
import com.alerts.AlertStrategy;
import com.alerts.BloodPressureStrategy;
import com.alerts.HeartRateStrategy;
import com.alerts.LowSaturationAlert;
import com.alerts.OxygenSaturationStrategy;
import com.data_management.Patient;

public class AlertStrategyTest {
    @Test
    public void testSystolicPressureJustBelowHighThreshold() {
        Patient patient = new Patient(1);
        patient.addRecord(179, "SystolicPressure", System.currentTimeMillis());
        AlertStrategy alert = new BloodPressureStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertTrue(alerts.isEmpty(), "No alert should be generated for systolic pressure just below 180");
    }

    @Test
    public void testDiastolicPressureJustAboveLowThreshold() {
        Patient patient = new Patient(1);
        patient.addRecord(61, "DiastolicPressure", System.currentTimeMillis());
        AlertStrategy alert = new BloodPressureStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertTrue(alerts.isEmpty(), "No alert should be generated for diastolic pressure just above 60");
    }

    @Test
    public void testNoAlertsWithinSafeRanges() {
        Patient patient = new Patient(1);
        patient.addRecord(120, "SystolicPressure", System.currentTimeMillis());
        patient.addRecord(80, "DiastolicPressure", System.currentTimeMillis());
        AlertStrategy alert = new BloodPressureStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertTrue(alerts.isEmpty(), "No alerts should be generated when pressures are within safe ranges");
    }

    @Test
    public void testMultipleAlertsForMultipleRecords() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(190, "SystolicPressure", now);
        patient.addRecord(55, "DiastolicPressure", now + 1000);
        AlertStrategy alert = new BloodPressureStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertEquals(2, alerts.size(), "Alerts should be generated for each critical threshold breach");
    }

    @Test
    public void testBoundaryConditions() {
        Patient patient = new Patient(1);
        patient.addRecord(180, "SystolicPressure", System.currentTimeMillis());
        patient.addRecord(120, "DiastolicPressure", System.currentTimeMillis());
        AlertStrategy alert = new BloodPressureStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertEquals(0, alerts.size(), "Alerts should not be generated for pressures exactly at the critical thresholds");
    }

    @Test
    public void testIncreasingTrend() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(110, "SystolicPressure", now);
        patient.addRecord(121, "SystolicPressure", now + 1000);
        patient.addRecord(133, "SystolicPressure", now + 2000);
        AlertStrategy alert = new BloodPressureStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertEquals(1, alerts.size(), "An alert should be triggered for an increasing trend.");
    }

    @Test
    public void testDecreasingTrend() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(140, "SystolicPressure", now);
        patient.addRecord(129, "SystolicPressure", now + 1000);
        patient.addRecord(118, "SystolicPressure", now + 2000);
        AlertStrategy alert = new BloodPressureStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertEquals(1, alerts.size(), "An alert should be triggered for a decreasing trend.");
    }

    @Test
    public void testSaturationExactlyAtThreshold() {
        Patient patient = new Patient(1);
        patient.addRecord(92, "Saturation", System.currentTimeMillis());
        AlertStrategy alert = new OxygenSaturationStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertTrue(alerts.isEmpty(), "No alert should be generated for saturation exactly at 92%");
    }

    @Test
    public void testSaturationJustAboveThreshold() {
        Patient patient = new Patient(1);
        patient.addRecord(93, "Saturation", System.currentTimeMillis());
        AlertStrategy alert = new OxygenSaturationStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertTrue(alerts.isEmpty(), "No alert should be generated for saturation above 92%");
    }

    @Test
    public void testMultipleRecords() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(95, "Saturation", now - 10000);
        patient.addRecord(90, "Saturation", now);
        patient.addRecord(91, "Saturation", now + 10000);
        AlertStrategy alert = new OxygenSaturationStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertEquals(2, alerts.size(), "Alerts should be generated for each record below 92%");
    }

    @Test
    public void testNoRecords() {
        Patient patient = new Patient(1);
        AlertStrategy alert = new OxygenSaturationStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertTrue(alerts.isEmpty(), "No alerts should be generated when no records are present");
    }

    @Test
    public void testRapidSequenceOfLowRecords() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(88, "Saturation", now);
        patient.addRecord(85, "Saturation", now + 5000);
        patient.addRecord(86, "Saturation", now + 10000);
        AlertStrategy alert = new OxygenSaturationStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertEquals(3, alerts.size(), "An alert should be generated for each low record in rapid sequence");
    }

    @Test
    public void ECGDataIsAbnormalTest() {
        Patient patient = new Patient(1);
        patient.addRecord(-1, "ECG", 1000);
        patient.addRecord(-0.75, "ECG", 1001);
        patient.addRecord(0.1, "ECG", 1002);
        patient.addRecord(-1, "ECG", 1003);
        patient.addRecord(-1, "ECG", 1004);
        AlertStrategy alert = new HeartRateStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertEquals(1, alerts.size(), "One Alert should be generated");
    }

    @Test
    public void ECGDataIsNormalTests() {
        Patient patient = new Patient(1);
        patient.addRecord(0, "ECG", 1000);
        AlertStrategy alert = new HeartRateStrategy();

        List<Alert> alerts = alert.checkAlert(patient);
        assertTrue(alerts.isEmpty(), "No alerts should be generated");
    }
}
