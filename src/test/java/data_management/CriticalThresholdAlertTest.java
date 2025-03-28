package data_management;

import com.alerts.Alert;
import com.alerts.CriticalThresholdAlert;
import com.alerts.TrendAlert;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CriticalThresholdAlertTest {

    @Test
    public void testSystolicPressureJustBelowHighThreshold() {
        Patient patient = new Patient(1);
        patient.addRecord(179, "SystolicPressure", System.currentTimeMillis());
        CriticalThresholdAlert alert = new CriticalThresholdAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertTrue(alerts.isEmpty(), "No alert should be generated for systolic pressure just below 180");
    }

    @Test
    public void testDiastolicPressureJustAboveLowThreshold() {
        Patient patient = new Patient(1);
        patient.addRecord(61, "DiastolicPressure", System.currentTimeMillis());
        CriticalThresholdAlert alert = new CriticalThresholdAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertTrue(alerts.isEmpty(), "No alert should be generated for diastolic pressure just above 60");
    }

    @Test
    public void testNoAlertsWithinSafeRanges() {
        Patient patient = new Patient(1);
        patient.addRecord(120, "SystolicPressure", System.currentTimeMillis());
        patient.addRecord(80, "DiastolicPressure", System.currentTimeMillis());
        CriticalThresholdAlert alert = new CriticalThresholdAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertTrue(alerts.isEmpty(), "No alerts should be generated when pressures are within safe ranges");
    }

    @Test
    public void testMultipleAlertsForMultipleRecords() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(190, "SystolicPressure", now);
        patient.addRecord(55, "DiastolicPressure", now + 1000);
        CriticalThresholdAlert alert = new CriticalThresholdAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(2, alerts.size(), "Alerts should be generated for each critical threshold breach");
    }

    @Test
    public void testBoundaryConditions() {
        Patient patient = new Patient(1);
        patient.addRecord(180, "SystolicPressure", System.currentTimeMillis());
        patient.addRecord(120, "DiastolicPressure", System.currentTimeMillis());
        CriticalThresholdAlert alert = new CriticalThresholdAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(0, alerts.size(), "Alerts should not be generated for pressures exactly at the critical thresholds");
    }

    @Test
    public void testIncreasingTrend() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(110, "SystolicPressure", now);
        patient.addRecord(121, "SystolicPressure", now + 1000);
        patient.addRecord(133, "SystolicPressure", now + 2000);

        TrendAlert alert = new TrendAlert();
        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(1, alerts.size(), "An alert should be triggered for an increasing trend.");
    }

    @Test
    public void testDecreasingTrend() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(140, "SystolicPressure", now);
        patient.addRecord(129, "SystolicPressure", now + 1000);
        patient.addRecord(118, "SystolicPressure", now + 2000);

        TrendAlert alert = new TrendAlert();
        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(1, alerts.size(), "An alert should be triggered for a decreasing trend.");
    }
}
