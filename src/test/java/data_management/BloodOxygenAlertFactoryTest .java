package data_management;
import com.alerts.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BloodOxygenAlertFactoryTest {

    @Test
    void testCreateAlert() {
        BloodOxygenAlertFactory factory = new BloodOxygenAlertFactory();
        String patientId = "123";
        String condition = "Low Oxygen";
        long timestamp = 999L;

        Alert alert = factory.createAlert(patientId, condition, timestamp);

        assertNotNull(alert, "Alert should not be null");
        assertEquals(patientId, alert.getPatientId(), "Patient ID should match");
        assertEquals("Blood Oxygen Alert: Low Oxygen", alert.getCondition(), "Condition message should be wrapped properly");
        assertEquals(timestamp, alert.getTimestamp(), "Timestamp should match input");
    }
}
