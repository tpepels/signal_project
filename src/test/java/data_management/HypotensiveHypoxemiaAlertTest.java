package data_management;

import com.alerts.Alert;
import com.alerts.HypotensiveHypoxemiaAlert;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HypotensiveHypoxemiaAlertTest {
    @Test
    public void testHypotensiveHypoxemia() {
        Patient patient = new Patient(1);
        patient.addRecord(88, "Saturation", 1000);
        patient.addRecord(85, "SystolicPressure", 1000);

        HypotensiveHypoxemiaAlert alert = new HypotensiveHypoxemiaAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(1, alerts.size());
        assertEquals("Hypotensive hypoxemia alert", alerts.get(0).getCondition());
    }
}
