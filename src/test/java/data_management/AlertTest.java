package data_management;

import com.alerts.alerts.Alert;
import com.alerts.alerts.TriggeredAlert;
import com.datamanagement.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlertTest {

    @Test
    void testAllMethods() {
        Alert alert = new TriggeredAlert(1, "Traumatic Brain Injury", 1714376789050L);

        int patientId = alert.getPatientId();
        String condition = alert.getCondition();
        long timestamp = alert.getTimestamp();

        assertEquals(1, patientId);
        assertEquals("Traumatic Brain Injury", condition);
        assertEquals(1714376789050L, timestamp);
        
    }
}