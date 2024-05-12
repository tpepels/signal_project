package data_management;

import com.alerts.Alert;
import com.alerts.LowSaturationAlert;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LowSaturationAlertTest {
    @Test
    public void testLowSaturation() {
        Patient patient = new Patient(1);
        patient.addRecord(90, "Saturation", System.currentTimeMillis());

        LowSaturationAlert alert = new LowSaturationAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertTrue(alerts.size() > 0);
        assertEquals("Low blood saturation alert", alerts.get(0).getCondition());
    }
}