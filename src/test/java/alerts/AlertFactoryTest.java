package alerts;
import com.alerts.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlertFactoryTest {

    @Test
    void testCreateAlert() {
        AlertFactory factory = new AlertFactory();
        String patientId = "patient123";
        String condition = "HighBloodPressure";
        long timestamp = 1700000000000L;

        Alert alert = factory.createAlert(patientId, condition, timestamp);

        assertNotNull(alert, "Alert should not be null");
        assertEquals(patientId, alert.getPatientId(), "Patient ID should match");
        assertEquals(condition, alert.getCondition(), "Condition should match");
        assertEquals(timestamp, alert.getTimestamp(), "Timestamp should match");
        assertTrue(alert.getMessage().contains(patientId), "Message should contain patient ID");
        assertTrue(alert.getMessage().contains(condition), "Message should contain condition");
        assertTrue(alert.getMessage().contains(Long.toString(timestamp)), "Message should contain timestamp");
    }

    @Test
    void testBloodPressureAlertFactory() {
        AlertFactory factory = new BloodPressureAlertFactory();
        String patientId = "patient456";
        String condition = "Low";
        long timestamp = 1700000001111L;

        Alert alert = factory.createAlert(patientId, condition, timestamp);

        assertNotNull(alert);
        assertEquals(patientId, alert.getPatientId());
        assertEquals("Blood Pressure Alert: " + condition, alert.getCondition());
        assertEquals(timestamp, alert.getTimestamp());
        assertTrue(alert.getMessage().contains("Blood Pressure Alert: " + condition));
    }

    @Test
    void testBloodOxygenAlertFactory() {
        AlertFactory factory = new BloodOxygenAlertFactory();
        String patientId = "patient789";
        String condition = "Critical";
        long timestamp = 1700000002222L;

        Alert alert = factory.createAlert(patientId, condition, timestamp);

        assertNotNull(alert);
        assertEquals(patientId, alert.getPatientId());
        assertEquals("Blood Oxygen Alert: " + condition, alert.getCondition());
        assertEquals(timestamp, alert.getTimestamp());
        assertTrue(alert.getMessage().contains("Blood Oxygen Alert: " + condition));
    }

    @Test
    void testECGAlertFactory() {
        AlertFactory factory = new ECGAlertFactory();
        String patientId = "patient999";
        String condition = "Arrhythmia";
        long timestamp = 1700000003333L;

        Alert alert = factory.createAlert(patientId, condition, timestamp);

        assertNotNull(alert);
        assertEquals(patientId, alert.getPatientId());
        assertEquals("ECG Alert: " + condition, alert.getCondition());
        assertEquals(timestamp, alert.getTimestamp());
        assertTrue(alert.getMessage().contains("ECG Alert: " + condition));
    }
}
