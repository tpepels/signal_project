package PatternsTest;

import com.alerts.AlertGenerator;
import com.alerts.Decorator.PriorityAlertDecorator;
import com.alerts.Decorator.RepeatedAlertDecorator;
import com.alerts.Factory.Alert;
import com.alerts.Strategy.*;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

class AlertGeneratorTest {
    private DataStorage dataStorage;
    private AlertGenerator alertGenerator;

    @BeforeEach
    void setUp() {
        dataStorage = new DataStorage();
        alertGenerator = new AlertGenerator(dataStorage);
    }

    @Test
    void testBloodPressureIncreasingTrendAlert() {
        Patient patient = new Patient(1); // Simulate patient with records
        patient.addRecord(130, "Systolic", System.currentTimeMillis() - 60000);
        patient.addRecord( 141, "Systolic", System.currentTimeMillis() - 30000);
        patient.addRecord(153, "Systolic", System.currentTimeMillis());

        BloodPressureStrategy strategy = new BloodPressureStrategy("Systolic");
        assertTrue(strategy.checkAlert(patient));
    }

    @Test
    void testSaturationCriticalAndRapidDropAlert() {
        Patient patient = new Patient(1); // Simulate patient with records
        patient.addRecord(97, "Saturation", System.currentTimeMillis() - 600000);
        patient.addRecord(90, "Saturation", System.currentTimeMillis());

        OxygenSaturationStrategy strategy = new OxygenSaturationStrategy();
        assertTrue(strategy.checkAlert(patient));
    }
    @Test
    void testCombinedAlertCondition() {
        Patient patient = new Patient(1);

        long currentTime = System.currentTimeMillis();
        patient.addRecord(100, "Systolic", currentTime - 360000); // 6 minutes ago
        patient.addRecord(95, "Systolic", currentTime - 240000);  // 4 minutes ago
        patient.addRecord(85, "Systolic", currentTime - 90000);   // 1.5 minutes ago

        patient.addRecord(95, "Saturation", currentTime - 180000); // 3 minutes ago
        patient.addRecord(93, "Saturation", currentTime - 120000); // 2 minutes ago
        patient.addRecord(91, "Saturation", currentTime - 60000);  // 1 minute ago

        CombinedAlertStrategy strategy = new CombinedAlertStrategy();
        strategy.checkAlert(patient);
        assertTrue(strategy.checkAlert(patient), "Combined alert should be triggered but it was not.");
    }

    @Test
    void testECGAlerts() {
        Patient patient = new Patient(1); // Simulate patient with ECG records
        patient.addRecord(45, "ECG", System.currentTimeMillis() - 10000);
        patient.addRecord(100, "ECG", System.currentTimeMillis());

        ECGStrategy strategy = new ECGStrategy();
        assertTrue(strategy.checkAlert(patient));
    }

    @Test
    void testManuallyTriggeredAlert() {
        Patient patient = new Patient(1); // Simulate patient with manual alert record
        patient.addRecord(1.0,"Alert", System.currentTimeMillis());

        ManuallyTriggeredAlertStrategy strategy = new ManuallyTriggeredAlertStrategy();
        assertTrue(strategy.checkAlert(patient));
    }

    @Test
    void testPriorityAlertDecorator() {
        Alert mockAlert = Mockito.mock(Alert.class);
        PriorityAlertDecorator priorityAlert = new PriorityAlertDecorator(mockAlert);

        priorityAlert.triggerAlert();

        // Verify that the original alert's triggerAlert method was called
        verify(mockAlert).triggerAlert();
        // Optionally check for system output if the decorator prints to console
    }
    @Test
    void testRepeatedAlertDecorator() throws InterruptedException {
        Alert mockAlert = Mockito.mock(Alert.class);
        long interval = 500; // 500ms for a fast test

        RepeatedAlertDecorator repeatedAlert = new RepeatedAlertDecorator(mockAlert, interval);
        repeatedAlert.triggerAlert();

        // Give enough time for the alert to potentially fire twice
        Thread.sleep(1200);

        // Verify that the alert has been triggered multiple times
        verify(mockAlert, atLeast(2)).triggerAlert();
    }

}
