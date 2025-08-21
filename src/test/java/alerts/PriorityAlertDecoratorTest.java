package alerts;
import com.alerts.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PriorityAlertDecoratorTest {

    private AlertInterface mockAlert;

    @BeforeEach
    void setUp() {
        mockAlert = mock(AlertInterface.class);
        when(mockAlert.getMessage()).thenReturn("Vitals unstable");
    }

    @Test
    void testGetMessageWithValidPriority() {
        PriorityAlertDecorator decorator = new PriorityAlertDecorator(mockAlert, 2); // "HIGH"
        String message = decorator.getMessage();

        assertTrue(message.startsWith("PRIORITY: HIGH"));
        assertTrue(message.contains("Vitals unstable"));
        verify(mockAlert, times(1)).getMessage();
    }

    @Test
    void testPriorityClampedAboveMax() {
        PriorityAlertDecorator decorator = new PriorityAlertDecorator(mockAlert, 10); // Exceeds max index
        assertEquals("CRITICAL", decorator.getPriority());
    }

    @Test
    void testPriorityClampedBelowMin() {
        PriorityAlertDecorator decorator = new PriorityAlertDecorator(mockAlert, -5); // Below min index
        assertEquals("LOW", decorator.getPriority());
    }

    @Test
    void testSetPriorityClamping() {
        PriorityAlertDecorator decorator = new PriorityAlertDecorator(mockAlert, 1); // "MEDIUM"
        decorator.setPriority(-1);
        assertEquals("LOW", decorator.getPriority());

        decorator.setPriority(99);
        assertEquals("CRITICAL", decorator.getPriority());
    }

    @Test
    void testPriorityLevels() {
        PriorityAlertDecorator decorator = new PriorityAlertDecorator(mockAlert, 3);
        assertEquals("CRITICAL", decorator.getPriority());

        decorator.setPriority(0);
        assertEquals("LOW", decorator.getPriority());
    }
}
