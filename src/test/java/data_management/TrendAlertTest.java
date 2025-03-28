package data_management;

import com.alerts.Alert;
import com.alerts.TrendAlert;
import com.data_management.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrendAlertTest {
    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient(1); // Assuming constructor sets up an empty list of records
    }

    @Test
    void testIncreasingTrend() {
        patient.addRecord(100, "SystolicPressure", 1000);
        patient.addRecord(110, "SystolicPressure", 2000);
        patient.addRecord(120, "SystolicPressure", 3000);
        TrendAlert alert = new TrendAlert();
        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(1, alerts.size(), "Should generate one alert for increasing trend");
    }

    @Test
    void testDecreasingTrend() {
        patient.addRecord(120, "SystolicPressure", 1000);
        patient.addRecord(110, "SystolicPressure", 2000);
        patient.addRecord(100, "SystolicPressure", 3000);
        TrendAlert alert = new TrendAlert();
        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(1, alerts.size(), "Should generate one alert for decreasing trend");
    }

    @Test
    void testNoTrend() {
        patient.addRecord(100, "SystolicPressure", 1000);
        patient.addRecord(105, "SystolicPressure", 2000);
        patient.addRecord(103, "SystolicPressure", 3000);
        TrendAlert alert = new TrendAlert();
        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(0, alerts.size(), "Should not generate any alerts as there is no significant trend");
    }

    @Test
    void testBoundaryTrend() {
        patient.addRecord(100, "SystolicPressure", 1000);
        patient.addRecord(105, "SystolicPressure", 2000);
        patient.addRecord(110, "SystolicPressure", 3000);
        TrendAlert alert = new TrendAlert();
        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(1, alerts.size(), "Should generate an alert for exactly 10 unit increase");
    }

    @Test
    void testInsufficientData() {
        patient.addRecord(100, "SystolicPressure", 1000);
        TrendAlert alert = new TrendAlert();
        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(0, alerts.size(), "Should not generate any alerts due to insufficient data");
    }
}
