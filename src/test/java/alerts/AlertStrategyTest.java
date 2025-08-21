package alerts;

import com.alerts.*;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlertStrategyTest {

    private List<PatientRecord> records;

    @BeforeEach
    void setup() {
        records = new ArrayList<>();
    }

    @Test
    void testHeartRateStrategyTriggersAlert() {
        AlertStrategy strategy = new HeartRateStrategy();
        records.add(new PatientRecord(1, 130, "Heart Rate", System.currentTimeMillis()));
        assertTrue(strategy.checkAlert(records));
    }

    @Test
    void testHeartRateStrategyDoesNotTriggerAlert() {
        AlertStrategy strategy = new HeartRateStrategy();
        records.add(new PatientRecord(1, 85, "Heart Rate", System.currentTimeMillis()));
        assertFalse(strategy.checkAlert(records));
    }

    @Test
    void testOxygenSaturationStrategyTriggersAlert() {
        AlertStrategy strategy = new OxygenSaturationStrategy();
        records.add(new PatientRecord(1, 93, "Saturation", System.currentTimeMillis()));
        assertTrue(strategy.checkAlert(records));
    }

    @Test
    void testOxygenSaturationStrategyDoesNotTriggerAlert() {
        AlertStrategy strategy = new OxygenSaturationStrategy();
        records.add(new PatientRecord(1, 89, "Saturation", System.currentTimeMillis()));
        assertFalse(strategy.checkAlert(records));
    }

    @Test
    void testBloodPressureStrategyTriggersAlert() {
        AlertStrategy strategy = new BloodPressureStrategy();
        records.add(new PatientRecord(1, 185, "SystolicBP", System.currentTimeMillis()));
        assertTrue(strategy.checkAlert(records));
    }

    @Test
    void testBloodPressureStrategyDoesNotTriggerAlert() {
        AlertStrategy strategy = new BloodPressureStrategy();
        records.add(new PatientRecord(1, 120, "SystolicBP", System.currentTimeMillis()));
        assertFalse(strategy.checkAlert(records));
    }

    @Test
    void testECGStrategyTriggersAlert() {
        AlertStrategy strategy = new ECGStrategy();
        records.add(new PatientRecord(1, 3.0, "ECG", System.currentTimeMillis()));
        assertTrue(strategy.checkAlert(records));
    }

    @Test
    void testECGStrategyDoesNotTriggerAlert() {
        AlertStrategy strategy = new ECGStrategy();
        records.add(new PatientRecord(1, 1.5, "ECG", System.currentTimeMillis()));
        assertFalse(strategy.checkAlert(records));
    }

    @Test
    void testEmptyRecordsReturnFalse() {
        assertFalse(new HeartRateStrategy().checkAlert(records));
        assertFalse(new OxygenSaturationStrategy().checkAlert(records));
        assertFalse(new BloodPressureStrategy().checkAlert(records));
        assertFalse(new ECGStrategy().checkAlert(records));
    }

    @Test
    void testConditionMessages() {
        assertEquals("Abnormal Heart Rate", new HeartRateStrategy().getCondition());
        assertEquals("Low Oxygen Saturation", new OxygenSaturationStrategy().getCondition());
        assertEquals("Abnormal Systolic Blood Pressure", new BloodPressureStrategy().getCondition());
        assertEquals("Abnormal ECG Data", new ECGStrategy().getCondition());
    }
}