package data_management;

import com.data_management.Patient;
import org.junit.jupiter.api.Test;
import com.alerts.*;
import static org.junit.jupiter.api.Assertions.*;

public class TriggeredAlertTest {

    @Test
    public void testTriggeredAlertReturnsAlert() {
        Patient patient = new Patient(1);
        long timestamp = System.currentTimeMillis();

        Alert alert = TriggeredAlert.processAlert(patient, "ALERT: triggered", timestamp);

        assertNotNull(alert);
        assertEquals("1", alert.getPatientId());
        assertEquals("Manual Triggered Alert", alert.getCondition());
        assertEquals(timestamp, alert.getTimestamp());
    }

    @Test
    public void testResolvedAlertReturnsAlert() {
        Patient patient = new Patient(2);
        long timestamp = System.currentTimeMillis();

        Alert alert = TriggeredAlert.processAlert(patient, "ALERT: resolved", timestamp);

        assertNotNull(alert);
        assertEquals("2", alert.getPatientId());
        assertEquals("Manual Resolved Alert", alert.getCondition());
        assertEquals(timestamp, alert.getTimestamp());
    }

    @Test
    public void testInvalidMessageReturnsNull() {
        Patient patient = new Patient(3);

        Alert alert = TriggeredAlert.processAlert(patient, "UNKNOWN MESSAGE", System.currentTimeMillis());
        assertNull(alert);
    }

    @Test
    public void testNullPatientReturnsNull() {
        Alert alert = TriggeredAlert.processAlert(null, "ALERT: triggered", System.currentTimeMillis());
        assertNull(alert);
    }

    @Test
    public void testNullMessageReturnsNull() {
        Patient patient = new Patient(4);
        Alert alert = TriggeredAlert.processAlert(patient, null, System.currentTimeMillis());
        assertNull(alert);
    }
    
}
