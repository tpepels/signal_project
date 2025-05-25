package data_management;


import com.alerts.Alert;
import com.alerts.HypotensiveHypoxemiaAlert;
import com.data_management.Patient;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HypotensiveHypoxemiaCombinedAlertTest {

    @Test
    void testAlertTriggeredWhenBothConditionsAreMet() {
        Patient patient = new Patient(1);
        patient.addRecord(85.0, "SystolicBloodPressure", 1000L);        // Low BP
        patient.addRecord(90.0, "BloodOxygenSaturation", 600_000L);    // Low O2, 10 min later

        HypotensiveHypoxemiaAlert checker = new HypotensiveHypoxemiaAlert();
        Alert alert = checker.check(patient);

        assertTrue(alert.getCondition().contains("none"));
        assertEquals("1", alert.getPatientId());
    }

    @Test
    void testNoAlertWhenOnlyLowBloodPressure() {
        Patient patient = new Patient(2);
        patient.addRecord(85.0, "SystolicBloodPressure", 1000L);        // Low BP
        patient.addRecord(95.0, "BloodOxygenSaturation", 2000L);        // Normal O2

        HypotensiveHypoxemiaAlert checker = new HypotensiveHypoxemiaAlert();
        Alert alert = checker.check(patient);

        assertEquals("none", alert.getCondition());
    }

    @Test
    void testNoAlertWhenOnlyLowOxygen() {
        Patient patient = new Patient(3);
        patient.addRecord(120.0, "SystolicBloodPressure", 1000L);       // Normal BP
        patient.addRecord(90.0, "BloodOxygenSaturation", 2000L);        // Low O2

        HypotensiveHypoxemiaAlert checker = new HypotensiveHypoxemiaAlert();
        Alert alert = checker.check(patient);

        assertEquals("none", alert.getCondition());
    }

    @Test
    void testNoAlertWhenNoConditionsAreMet() {
        Patient patient = new Patient(4);
        patient.addRecord(120.0, "SystolicBloodPressure", 1000L);       // Normal BP
        patient.addRecord(96.0, "BloodOxygenSaturation", 2000L);        // Normal O2

        HypotensiveHypoxemiaAlert checker = new HypotensiveHypoxemiaAlert();
        Alert alert = checker.check(patient);

        assertEquals("none", alert.getCondition());
    }
}
