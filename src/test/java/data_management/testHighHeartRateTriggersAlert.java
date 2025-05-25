package data_management;
import com.alerts.*;

import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeartRateStrategyTest {

    @Test
    void testHighHeartRateTriggersAlert() {
        Patient patient = new Patient(1);
        patient.addRecord(130.0, "HeartRate", 1000L); 

        HeartRateStrategy strategy = new HeartRateStrategy();
        Alert alert = strategy.checkAlert(patient);

        assertTrue(alert.getCondition().contains("Abnormal Heart Rate"));
        assertEquals("1", alert.getPatientId());
        assertEquals(1000L, alert.getTimestamp());
    }

    @Test
    void testLowHeartRateTriggersAlert() {
        Patient patient = new Patient(2);
        patient.addRecord(45.0, "HeartRate", 2000L);  

        HeartRateStrategy strategy = new HeartRateStrategy();
        Alert alert = strategy.checkAlert(patient);

        assertTrue(alert.getCondition().contains("Abnormal Heart Rate"));
        assertEquals("2", alert.getPatientId());
        assertEquals(2000L, alert.getTimestamp());
    }

    @Test
    void testNormalHeartRateDoesNotTriggerAlert() {
        Patient patient = new Patient(3);
        patient.addRecord(80.0, "HeartRate", 3000L);  

        HeartRateStrategy strategy = new HeartRateStrategy();
        Alert alert = strategy.checkAlert(patient);

        assertEquals("none", alert.getCondition());
        assertEquals("3", alert.getPatientId());
    }

    @Test
    void testNonHeartRateRecordsAreIgnored() {
        Patient patient = new Patient(4);
        patient.addRecord(120.0, "SystolicBloodPressure", 1000L);
        patient.addRecord(85.0, "DiastolicBloodPressure", 2000L);

        HeartRateStrategy strategy = new HeartRateStrategy();
        Alert alert = strategy.checkAlert(patient);

        assertEquals("none", alert.getCondition());
        assertEquals("4", alert.getPatientId());
    }
}
