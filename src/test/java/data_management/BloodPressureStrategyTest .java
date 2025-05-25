package data_management;

import com.data_management.Patient;
import org.junit.jupiter.api.Test;
import com.alerts.*;
import static org.junit.jupiter.api.Assertions.*;

class BloodPressureStrategyTest {

    @Test
    void testCriticalSystolicHigh() {
        Patient patient = new Patient(1);
        patient.addRecord(190.0, "SystolicBloodPressure", 1000L);  // Above upper limit (180)

        BloodPressureStrategy strategy = new BloodPressureStrategy();
        Alert alert = strategy.checkAlert(patient);

        assertTrue(alert.getCondition().contains("Critical SystolicBloodPressure"));
    }

    @Test
    void testCriticalDiastolicLow() {
        Patient patient = new Patient(2);
        patient.addRecord(50.0, "DiastolicBloodPressure", 1000L);  // Below lower limit (60)

        BloodPressureStrategy strategy = new BloodPressureStrategy();
        Alert alert = strategy.checkAlert(patient);

        assertTrue(alert.getCondition().contains("Critical DiastolicBloodPressure"));
    }

    @Test
    void testSystolicIncreasingTrend() {
        Patient patient = new Patient(3);
        patient.addRecord(100.0, "SystolicBloodPressure", 1000L);
        patient.addRecord(115.0, "SystolicBloodPressure", 2000L);
        patient.addRecord(130.0, "SystolicBloodPressure", 3000L);  // Increasing trend

        BloodPressureStrategy strategy = new BloodPressureStrategy();
        Alert alert = strategy.checkAlert(patient);

        assertTrue(alert.getCondition().contains("Increasing Trend in SystolicBloodPressure"));
    }

   @Test
void testDiastolicDecreasingTrend() {
    Patient patient = new Patient(5);
    patient.addRecord(100.0, "DiastolicBloodPressure", 1000L);
    patient.addRecord(85.0, "DiastolicBloodPressure", 2000L);
    patient.addRecord(70.0, "DiastolicBloodPressure", 3000L);

    BloodPressureStrategy strategy = new BloodPressureStrategy();
    Alert alert = strategy.checkAlert(patient);

    assertTrue(alert.getCondition().contains("Decreasing Trend in DiastolicBloodPressure"));
    assertEquals("5", alert.getPatientId());
}

    @Test
    void testNoAlertWhenNormal() {
        Patient patient = new Patient(5);
        patient.addRecord(120.0, "SystolicBloodPressure", 1000L);
        patient.addRecord(121.0, "SystolicBloodPressure", 2000L);
        patient.addRecord(122.0, "SystolicBloodPressure", 3000L);

        patient.addRecord(80.0, "DiastolicBloodPressure", 1000L);
        patient.addRecord(81.0, "DiastolicBloodPressure", 2000L);
        patient.addRecord(82.0, "DiastolicBloodPressure", 3000L);

        BloodPressureStrategy strategy = new BloodPressureStrategy();
        Alert alert = strategy.checkAlert(patient);

        assertEquals("none", alert.getCondition());
    }
}
