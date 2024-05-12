package data_management;

import com.alerts.Alert;
import com.alerts.TrendAlert;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrendAlertTest {
    @Test
    public void testIncreasingTrendInBloodPressure() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(120, "SystolicPressure", now - 20000);
        patient.addRecord(130, "SystolicPressure", now - 10000);
        patient.addRecord(145, "SystolicPressure", now);

        TrendAlert alert = new TrendAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(1, alerts.size());
        assertEquals("Blood pressure trend alert", alerts.get(0).getCondition());
    }
}
