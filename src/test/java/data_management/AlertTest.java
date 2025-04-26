package data_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.alerts.Alert;

class AlertTest {

    @Test
    void testAlertInitialization() {
        String patientId = "12345";
        String condition = "High Blood Pressure";
        long timestamp = System.currentTimeMillis();

        Alert alert = new Alert(patientId, condition, timestamp);

        assertEquals(patientId, alert.getPatientId());
        assertEquals(condition, alert.getCondition());
        assertEquals(timestamp, alert.getTimestamp());
    }
}
