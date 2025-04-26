package data_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alerts.Alert;
import com.alerts.AlertFactory;
import com.alerts.BloodOxygenAlert;
import com.alerts.BloodOxygenAlertFactory;
import com.alerts.BloodPressureAlert;
import com.alerts.BloodPressureAlertFactory;
import com.alerts.ECGAlert;
import com.alerts.ECGAlertFactory;

class AlertFactoryTest {

    private AlertFactory bpFactory;
    private AlertFactory boFactory;
    private AlertFactory ecgFactory;

    @BeforeEach
    void setUp() {
        bpFactory = new BloodPressureAlertFactory();
        boFactory = new BloodOxygenAlertFactory();
        ecgFactory = new ECGAlertFactory();
    }

    @Test
    void testBloodPressureAlertFactoryCreatesCorrectAlert() {
        Alert alert = bpFactory.createAlert("P001", "High BP", 123456L);
        assertNotNull(alert);
        assertTrue(alert instanceof BloodPressureAlert);
        assertEquals("P001", alert.getPatientId());
        assertEquals("High BP", alert.getCondition());
        assertEquals(123456L, alert.getTimestamp());
    }

    @Test
    void testBloodOxygenAlertFactoryCreatesCorrectAlert() {
        Alert alert = boFactory.createAlert("P002", "Low Oxygen", 123457L);
        assertNotNull(alert);
        assertTrue(alert instanceof BloodOxygenAlert);
        assertEquals("P002", alert.getPatientId());
        assertEquals("Low Oxygen", alert.getCondition());
        assertEquals(123457L, alert.getTimestamp());
    }

    @Test
    void testECGAlertFactoryCreatesCorrectAlert() {
        Alert alert = ecgFactory.createAlert("P003", "Irregular ECG", 123458L);
        assertNotNull(alert);
        assertTrue(alert instanceof ECGAlert);
        assertEquals("P003", alert.getPatientId());
        assertEquals("Irregular ECG", alert.getCondition());
        assertEquals(123458L, alert.getTimestamp());
    }
}
