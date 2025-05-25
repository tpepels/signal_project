package data_management;


import com.alerts.Alert;
import com.alerts.BloodSaturationDataAlerts;
import com.data_management.Patient;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BloodSaturationDataAlertsTest {

    @Test
    void testLowSaturationAlert() {
        Patient patient = new Patient(1);
        patient.addRecord(89.0, "BloodOxygenSaturation", 1000L);  // Below 92

        BloodSaturationDataAlerts checker = new BloodSaturationDataAlerts();
        Alert alert = checker.check(patient);

        assertTrue(alert.getCondition().contains("Low Blood Oxygen Saturation"));
        assertEquals("1", alert.getPatientId());
    }

    @Test
    void testRapidDropAlert() {
        Patient patient = new Patient(2);
        patient.addRecord(97.0, "BloodOxygenSaturation", 1000L);     // initial
        patient.addRecord(91.5, "BloodOxygenSaturation", 2000L);     // not enough
        patient.addRecord(91.0, "BloodOxygenSaturation", 2500L);     // still not enough
        patient.addRecord(90.0, "BloodOxygenSaturation", 3000L);     // drop = 7 (alert)

        BloodSaturationDataAlerts checker = new BloodSaturationDataAlerts();
        Alert alert = checker.check(patient);

        assertTrue(alert.getCondition().contains("Rapid Drop in Blood Oxygen Saturation"));
        assertEquals("2", alert.getPatientId());
    }

    @Test
    void testNoAlertCondition() {
        Patient patient = new Patient(3);
        patient.addRecord(96.0, "BloodOxygenSaturation", 1000L);
        patient.addRecord(95.5, "BloodOxygenSaturation", 2000L);
        patient.addRecord(94.5, "BloodOxygenSaturation", 3000L); // small drop, but not enough

        BloodSaturationDataAlerts checker = new BloodSaturationDataAlerts();
        Alert alert = checker.check(patient);

        assertEquals("none", alert.getCondition());
    }
}
