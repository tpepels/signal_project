package alert;

import com.alerts.Alert;
import com.alerts.AlertCondition;
import com.data_management.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alerts.RepeatedAlertDecorator;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RepeatedAlertDecoratorTest {

    private AlertCondition originalCondition;
    private Patient patient;

    @BeforeEach
    void setUp() {
        originalCondition = mock(AlertCondition.class);
        patient = new Patient(1);
    }

    @Test
    void testRepeatedChecks() {
        Alert alert = new Alert("1", "High Blood Pressure", System.currentTimeMillis());
        List<Alert> alerts = List.of(alert);

        when(originalCondition.checkCondition(patient)).thenReturn(alerts);

        int repeatCount = 5;
        RepeatedAlertDecorator decorator = new RepeatedAlertDecorator(originalCondition, repeatCount);
        List<Alert> repeatedAlerts = decorator.checkCondition(patient);

        verify(originalCondition, times(repeatCount)).checkCondition(patient);
        assertEquals(repeatCount * alerts.size(), repeatedAlerts.size(), "Alerts should be repeated the specified number of times.");
    }

    @Test
    void testConsistentAlertsAcrossCalls() {
        Alert alert = new Alert("1", "Low iron", System.currentTimeMillis());
        List<Alert> alerts = List.of(alert);

        when(originalCondition.checkCondition(patient)).thenReturn(alerts);

        int repeatCount = 3;
        RepeatedAlertDecorator decorator = new RepeatedAlertDecorator(originalCondition, repeatCount);
        List<Alert> repeatedAlerts = decorator.checkCondition(patient);

        assertEquals(repeatCount * alerts.size(), repeatedAlerts.size(), "The same alerts should be returned on each call.");
        repeatedAlerts.forEach(a -> assertEquals("Low iron", a.getCondition(), "Each alert should have the same condition."));
    }

    @Test
    void testDifferentAlertsInEachCall() {
        Alert alert1 = new Alert("1", "Heart stopped", System.currentTimeMillis());
        Alert alert2 = new Alert("1", "No brain function", System.currentTimeMillis());
        Alert alert3 = new Alert("1", "Blood clog", System.currentTimeMillis());

        when(originalCondition.checkCondition(patient))
                .thenReturn(List.of(alert1))
                .thenReturn(List.of(alert2))
                .thenReturn(List.of(alert3));

        int repeatCount = 3;
        RepeatedAlertDecorator decorator = new RepeatedAlertDecorator(originalCondition, repeatCount);
        List<Alert> repeatedAlerts = decorator.checkCondition(patient);

        assertEquals(repeatCount, repeatedAlerts.size(), "A different alert should be returned on each call.");
        assertTrue(repeatedAlerts.containsAll(Arrays.asList(alert1, alert2, alert3)), "All different alerts should be included.");
    }
}
