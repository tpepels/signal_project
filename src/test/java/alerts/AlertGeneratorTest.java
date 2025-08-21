package alerts;
import com.alerts.*;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AlertGeneratorTest {

    private DataStorage dataStorage;
    private List<AlertStrategy> alertStrategies;
    private List<Alert> triggeredAlerts;

    @BeforeEach
    void setUp() {
        dataStorage = DataStorage.getInstance();
        alertStrategies = new ArrayList<>();
        triggeredAlerts = new ArrayList<>();
    }

    @Test
    void testEvaluateData_triggersAlertWhenConditionMet() {
        // Arrange: Add a patient and a record
        int patientId = 42;
        dataStorage.addPatientData(patientId, 120.0, "BloodPressure", System.currentTimeMillis());
        Patient patient = new Patient(patientId);

        // Mock AlertStrategy that always triggers
        AlertStrategy alwaysTriggerStrategy = new AlertStrategy() {
            @Override
            public boolean checkAlert(List<PatientRecord> records) {
                return true;
            }
            @Override
            public String getCondition() {
                return "HighBloodPressure";
            }
        };
        alertStrategies.add(alwaysTriggerStrategy);

        AlertGenerator generator = new AlertGenerator(dataStorage, alertStrategies, triggeredAlerts);

        // Act
        generator.evaluateData(patient);

        // Assert
        List<Alert> alerts = generator.getTriggeredAlerts();
        assertEquals(1, alerts.size());
        Alert alert = alerts.get(0);
        assertEquals(String.valueOf(patientId), alert.getPatientId());
        assertEquals("HighBloodPressure", alert.getCondition());
    }

    @Test
    void testEvaluateData_doesNotTriggerAlertWhenConditionNotMet() {
        // Arrange: Add a patient and a record
        int patientId = 99;
        dataStorage.addPatientData(patientId, 80.0, "HeartRate", System.currentTimeMillis());
        Patient patient = new Patient(patientId);

        // Mock AlertStrategy that never triggers
        AlertStrategy neverTriggerStrategy = new AlertStrategy() {
            @Override
            public boolean checkAlert(List<PatientRecord> records) {
                return false;
            }
            @Override
            public String getCondition() {
                return "LowHeartRate";
            }
        };
        alertStrategies.add(neverTriggerStrategy);

        AlertGenerator generator = new AlertGenerator(dataStorage, alertStrategies, triggeredAlerts);

        // Act
        generator.evaluateData(patient);

        // Assert
        assertTrue(generator.getTriggeredAlerts().isEmpty());
    }
}
