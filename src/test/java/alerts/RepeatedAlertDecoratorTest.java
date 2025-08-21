package alerts;
import com.alerts.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RepeatedAlertDecoratorTest {

    private AlertInterface mockAlert;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        // Mock the base alert
        mockAlert = mock(AlertInterface.class);
        when(mockAlert.getMessage()).thenReturn("Base Alert");
        when(mockAlert.getCondition()).thenReturn("MEDIUM");

        // Redirect System.out for capturing timer prints
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        // Restore original System.out
        System.setOut(originalOut);
    }

    @Test
    void testGetMessageFormat() {
        RepeatedAlertDecorator decorator = new RepeatedAlertDecorator(mockAlert, 5, 1000);
        String msg = decorator.getMessage();

        assertTrue(msg.startsWith("[REPEATED 5x]"));
        assertTrue(msg.contains("Base Alert"));
    }

    @Test
    void testRepeatedCheckOutputsCorrectCount() throws InterruptedException {
        RepeatedAlertDecorator decorator = new RepeatedAlertDecorator(mockAlert, 3, 300); // fast checks
        decorator.checkConditionRepeatedly();

        // Wait slightly longer than 3 intervals
        Thread.sleep(1000);

        String output = outContent.toString();
        long checkCount = output.lines().filter(line -> line.contains("Re-check")).count();

        assertEquals(3, checkCount, "Should check condition exactly 3 times");
    }

    @Test
    void testCancelStopsEarly() throws InterruptedException {
        RepeatedAlertDecorator decorator = new RepeatedAlertDecorator(mockAlert, 10, 500);
        decorator.checkConditionRepeatedly();

        Thread.sleep(1000); // allow 2-ish checks
        decorator.cancelChecks();


        Thread.sleep(1000); // wait to see if it restarts (it shouldn’t)

        String output = outContent.toString();
        long checkCount = output.lines().filter(line -> line.contains("Re-check")).count();

        assertTrue(checkCount >= 2 && checkCount < 10, "Should have stopped before full repeat count");
    }
}
