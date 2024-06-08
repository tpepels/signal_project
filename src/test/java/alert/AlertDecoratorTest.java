package alert;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.alerts.Alert;
import com.alerts.AlertCondition;
import com.alerts.AlertDecorator;
import com.data_management.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class AlertDecoratorTest {

    private AlertCondition originalCondition;
    private Patient patient;

    @BeforeEach
    void setUp() {
        originalCondition = mock(AlertCondition.class);
        patient = new Patient(123);
    }

    @Test
    void testAlertDecoratorForwardsCheckCondition() {
        Alert alert = new Alert("123", "Heart rate too high", System.currentTimeMillis());
        List<Alert> expectedAlerts = List.of(alert);

        when(originalCondition.checkCondition(patient)).thenReturn(expectedAlerts);

        AlertDecorator decorator = new AlertDecorator(originalCondition);
        List<Alert> alerts = decorator.checkCondition(patient);

        verify(originalCondition, times(1)).checkCondition(patient);
        assertEquals(expectedAlerts, alerts);
    }
}