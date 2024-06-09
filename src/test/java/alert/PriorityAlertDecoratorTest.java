package alert;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.alerts.Alert;
import com.alerts.AlertCondition;
import com.alerts.PriorityAlertDecorator;
import com.data_management.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Arrays;

class PriorityAlertDecoratorTest {

    private AlertCondition originalCondition;
    private Patient patient;

    @BeforeEach
    void setUp() {
        originalCondition = mock(AlertCondition.class);
        patient = new Patient(1);
    }

    @Test
    void testPriorityUpdatesSetting() {
        Alert alert1 = new Alert("1", "Brain aneurysm", System.currentTimeMillis());
        Alert alert2 = new Alert("1", "Stroke", System.currentTimeMillis());
        List<Alert> alerts = Arrays.asList(alert1, alert2);

        when(originalCondition.checkCondition(patient)).thenReturn(alerts);

        PriorityAlertDecorator decorator = new PriorityAlertDecorator(originalCondition);

        decorator.checkCondition(patient);
        assertEquals("Low", alerts.get(0).getPriority(), "Alert 1 priority should be incremented to Medium.");
        assertEquals("Low", alerts.get(1).getPriority(), "Alert 2 priority should be incremented to Medium.");

        decorator.checkCondition(patient);
        assertEquals("Medium", alerts.get(0).getPriority(), "Alert 1 priority should be incremented to High.");
        assertEquals("Medium", alerts.get(1).getPriority(), "Alert 2 priority should be incremented to High.");
    }

    @Test
    void testPrioritySetting() {
        Alert alert1 = new Alert("1", "Seizure", System.currentTimeMillis());
        Alert alert2 = new Alert("1", "Allergic reaction", System.currentTimeMillis());
        List<Alert> alerts = Arrays.asList(alert1, alert2);

        when(originalCondition.checkCondition(patient)).thenReturn(alerts);

        PriorityAlertDecorator decorator = new PriorityAlertDecorator(originalCondition);

        decorator.setPriorityForAlerts(alerts, "High");
        assertEquals("High", alerts.get(0).getPriority(), "Alert 1 priority should be directly set to High.");
        assertEquals("High", alerts.get(1).getPriority(), "Alert 2 priority should be directly set to High.");

        decorator.checkCondition(patient);
        assertEquals("Critical", alerts.get(0).getPriority(), "Alert 1 priority should be incremented to Critical.");
        assertEquals("Critical", alerts.get(1).getPriority(), "Alert 2 priority should be incremented to Critical.");
    }

}
