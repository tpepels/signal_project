package data_management;
import com.alerts.*;

import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OxygenSaturationStrategyTest {

    @Test
    void testRapidDropTriggersAlert() {
        Patient patient = new Patient(1);
        patient.addRecord(97.0, "BloodOxygenSaturation", 1000L);
        patient.addRecord(91.0, "BloodOxygenSaturation", 200000L);  // 6.0 drop within 10 minutes

        OxygenSaturationStrategy strategy = new OxygenSaturationStrategy();
        Alert alert = strategy.checkAlert(patient);

        assertTrue(alert.getCondition().contains("Rapid Drop"));
        assertEquals("1", alert.getPatientId());
    }

    @Test
    void testLowOxygenTriggersAlert() {
        Patient patient = new Patient(2);
        patient.addRecord(91.5, "BloodOxygenSaturation", 5000L);  // Below 92%

        OxygenSaturationStrategy strategy = new OxygenSaturationStrategy();
        Alert alert = strategy.checkAlert(patient);

        assertTrue(alert.getCondition().contains("Low Blood Oxygen Saturation"));
        assertEquals("2", alert.getPatientId());
        assertEquals(5000L, alert.getTimestamp());
    }

    @Test
    void testNoAlertWhenOxygenIsNormal() {
        Patient patient = new Patient(3);
        patient.addRecord(96.0, "BloodOxygenSaturation", 1000L);
        patient.addRecord(95.5, "BloodOxygenSaturation", 2000L);

        OxygenSaturationStrategy strategy = new OxygenSaturationStrategy();
        Alert alert = strategy.checkAlert(patient);

        assertEquals("none", alert.getCondition());
        assertEquals("3", alert.getPatientId());
    }

    @Test
    void testIgnoreNonOxygenData() {
        Patient patient = new Patient(4);
        patient.addRecord(100.0, "SystolicBloodPressure", 1000L);
        patient.addRecord(80.0, "DiastolicBloodPressure", 2000L);

        OxygenSaturationStrategy strategy = new OxygenSaturationStrategy();
        Alert alert = strategy.checkAlert(patient);

        assertEquals("none", alert.getCondition());
        assertEquals("4", alert.getPatientId());
    }

    @Test
    void testRapidDropOutsideTimeWindowDoesNotTrigger() {
        Patient patient = new Patient(5);
        patient.addRecord(97.0, "BloodOxygenSaturation", 1000L);
        patient.addRecord(91.0, "BloodOxygenSaturation", 700000L); // > 10 min apart

        OxygenSaturationStrategy strategy = new OxygenSaturationStrategy();
        Alert alert = strategy.checkAlert(patient);

       
        assertTrue(alert.getCondition().contains("Low Blood Oxygen Saturation"));
        assertEquals("5", alert.getPatientId());
    }
}
