package data_management;

import com.alerts.Alert;
import com.alerts.CriticalThresholdAlert;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CriticalThresholdAlertTest {
    @Test
    public void testSystolicPressureCriticalHigh() {
        Patient patient = new Patient(1);
        patient.addRecord(185, "SystolicPressure", System.currentTimeMillis());
        CriticalThresholdAlert alert = new CriticalThresholdAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(1, alerts.size());
        assertEquals("Blood pressure critical threshold alert", alerts.get(0).getCondition());
    }

    @Test
    public void testDiastolicPressureCriticalLow() {
        Patient patient = new Patient(1);
        patient.addRecord(55, "DiastolicPressure", System.currentTimeMillis());
        CriticalThresholdAlert alert = new CriticalThresholdAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(1, alerts.size());
        assertEquals("Blood pressure critical threshold alert", alerts.get(0).getCondition());
    }
}
