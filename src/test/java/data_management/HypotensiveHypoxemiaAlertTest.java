package data_management;

import com.alerts.Alert;
import com.alerts.HypotensiveHypoxemiaAlert;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class HypotensiveHypoxemiaAlertTest {

    @Test
    public void testJustAboveThresholds() {
        Patient patient = new Patient(1);
        patient.addRecord(92, "Saturation", 1000);
        patient.addRecord(90, "SystolicPressure", 1000);

        HypotensiveHypoxemiaAlert alert = new HypotensiveHypoxemiaAlert();
        List<Alert> alerts = alert.checkCondition(patient);
        assertTrue(alerts.isEmpty(), "No alerts should be generated as values are just above thresholds");
    }

    @Test
    public void testNoMatchingBloodPressureRecords() {
        Patient patient = new Patient(1);
        patient.addRecord(88, "Saturation", 1000);
        // No blood pressure records added

        HypotensiveHypoxemiaAlert alert = new HypotensiveHypoxemiaAlert();
        List<Alert> alerts = alert.checkCondition(patient);
        assertTrue(alerts.isEmpty(), "No alerts should be generated without matching blood pressure records");
    }

    @Test
    public void testBloodPressureNotLowEnough() {
        Patient patient = new Patient(1);
        patient.addRecord(88, "Saturation", 1000);
        patient.addRecord(91, "SystolicPressure", 1000);

        HypotensiveHypoxemiaAlert alert = new HypotensiveHypoxemiaAlert();
        List<Alert> alerts = alert.checkCondition(patient);
        assertTrue(alerts.isEmpty(), "No alerts should be generated as blood pressure is not low enough");
    }

    @Test
    public void testMultipleValidRecords() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(88, "Saturation", now - 5000);
        patient.addRecord(85, "SystolicPressure", now - 5000);
        patient.addRecord(87, "Saturation", now);
        patient.addRecord(84, "SystolicPressure", now);

        HypotensiveHypoxemiaAlert alert = new HypotensiveHypoxemiaAlert();
        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(2, alerts.size(), "Alerts should be generated for each valid hypotensive hypoxemia condition");
    }

    @Test
    public void testTimeWindowForSystolicPressure() {
        Patient patient = new Patient(1);
        patient.addRecord(88, "Saturation", 1000);
        patient.addRecord(85, "SystolicPressure", 3000); // Outside of the +-1000ms window

        HypotensiveHypoxemiaAlert alert = new HypotensiveHypoxemiaAlert();
        List<Alert> alerts = alert.checkCondition(patient);
        assertTrue(alerts.isEmpty(), "No alert should be generated as the blood pressure record is outside the time window");
    }
}
