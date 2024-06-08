package alert;

import com.alerts.Alert;
import com.alerts.AlertCondition;
import com.alerts.FilterAlertDecorator;
import com.data_management.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilterAlertDecoratorTest {

    private AlertCondition originalCondition;
    private Patient patient;

    @BeforeEach
    void setUp() {
        originalCondition = mock(AlertCondition.class);
        patient = new Patient(123);
    }

    @Test
    void testWithNoAlerts() {
        when(originalCondition.checkCondition(patient)).thenReturn(Collections.emptyList());

        Predicate<Alert> anyCondition = alert -> true;
        FilterAlertDecorator decorator = new FilterAlertDecorator(originalCondition, anyCondition);

        assertTrue(decorator.checkCondition(patient).isEmpty(), "No alerts should be returned.");
    }

    @Test
    void testWithAllAlertsPassing() {
        Alert alert1 = new Alert("123", "One urgent condition", System.currentTimeMillis());
        Alert alert2 = new Alert("123", "Another urgent condition", System.currentTimeMillis());
        List<Alert> allAlerts = Arrays.asList(alert1, alert2);

        when(originalCondition.checkCondition(patient)).thenReturn(allAlerts);

        Predicate<Alert> urgentFilter = alert -> alert.getCondition().contains("urgent");
        FilterAlertDecorator decorator = new FilterAlertDecorator(originalCondition, urgentFilter);

        List<Alert> filteredAlerts = decorator.checkCondition(patient);
        assertEquals(2, filteredAlerts.size(), "All alerts should pass the filter.");
    }

    @Test
    void testWithNoAlertsPassing() {
        Alert alert1 = new Alert("123", "Normal condition", System.currentTimeMillis());
        Alert alert2 = new Alert("123", "Stable condition", System.currentTimeMillis());
        List<Alert> alerts = Arrays.asList(alert1, alert2);

        when(originalCondition.checkCondition(patient)).thenReturn(alerts);

        Predicate<Alert> criticalFilter = alert -> alert.getCondition().contains("Critical");
        FilterAlertDecorator decorator = new FilterAlertDecorator(originalCondition, criticalFilter);

        assertTrue(decorator.checkCondition(patient).isEmpty(), "No alerts should pass the filter.");
    }

    @Test
    void testWithNullConditions() {
        Alert alert1 = new Alert("123", null, System.currentTimeMillis());
        Alert alert2 = new Alert("123", "Stable condition", System.currentTimeMillis());
        List<Alert> alerts = Arrays.asList(alert1, alert2);

        when(originalCondition.checkCondition(patient)).thenReturn(alerts);

        Predicate<Alert> nullSafeFilter = alert -> alert.getCondition() != null && alert.getCondition().contains("Critical");
        FilterAlertDecorator decorator = new FilterAlertDecorator(originalCondition, nullSafeFilter);

        assertTrue(decorator.checkCondition(patient).isEmpty(), "No alerts should pass the filter, including handling null safely.");
    }
}