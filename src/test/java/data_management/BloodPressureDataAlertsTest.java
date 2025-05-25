package data_management;

import com.alerts.Alert;
import com.alerts.BloodPressureDataAlerts;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BloodPressureDataAlertsTest {

    @Test
    void testSystolicCriticalHighAlert() {
        Patient patient = new Patient(1);
        patient.addRecord(190.0, "SystolicBloodPressure", 1000L);

        BloodPressureDataAlerts checker = new BloodPressureDataAlerts();
        Alert alert = checker.check(patient);

        assertTrue(alert.getCondition().contains("Critical SystolicBloodPressure"));
    }

    @Test
    void testDiastolicCriticalLowAlert() {
        Patient patient = new Patient(2);
        patient.addRecord(55.0, "DiastolicBloodPressure", 1000L);

        BloodPressureDataAlerts checker = new BloodPressureDataAlerts();
        Alert alert = checker.check(patient);

        assertTrue(alert.getCondition().contains("Critical DiastolicBloodPressure"));
    }

    @Test
    void testSystolicIncreasingTrendAlert() {
        Patient patient = new Patient(3);
        patient.addRecord(100.0, "SystolicBloodPressure", 1000L);
        patient.addRecord(112.0, "SystolicBloodPressure", 2000L);
        patient.addRecord(125.0, "SystolicBloodPressure", 3000L);

        BloodPressureDataAlerts checker = new BloodPressureDataAlerts();
        Alert alert = checker.check(patient);

        assertTrue(alert.getCondition().contains("Increasing Trend in SystolicBloodPressure"));
    }

    @Test
    void testDiastolicDecreasingTrendAlert() {
        Patient patient = new Patient(4);
        patient.addRecord(100.0, "DiastolicBloodPressure", 1000L);
        patient.addRecord(85.0, "DiastolicBloodPressure", 2000L);
        patient.addRecord(70.0, "DiastolicBloodPressure", 3000L);

        BloodPressureDataAlerts checker = new BloodPressureDataAlerts();
        Alert alert = checker.check(patient);

        assertTrue(alert.getCondition().contains("Decreasing Trend in DiastolicBloodPressure"));
    }

    @Test
    void testNoAlertCondition() {
        Patient patient = new Patient(5);
        patient.addRecord(110.0, "SystolicBloodPressure", 1000L);
        patient.addRecord(115.0, "DiastolicBloodPressure", 2000L);

        BloodPressureDataAlerts checker = new BloodPressureDataAlerts();
        Alert alert = checker.check(patient);

        assertEquals("none", alert.getCondition());
    }
}
