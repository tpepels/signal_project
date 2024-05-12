package data_management;

import com.alerts.Alert;
import com.alerts.RapidDropAlert;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RapidDropAlertTest {
    @Test
    public void testRapidDropInSaturation() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(95, "Saturation", now);
        patient.addRecord(88, "Saturation", now + 300000); // 5 minutes later

        RapidDropAlert alert = new RapidDropAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(1, alerts.size());
        assertEquals("Rapid blood saturation drop alert", alerts.get(0).getCondition());
    }
}
