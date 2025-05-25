package data_management;
import com.alerts.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class RepeatedAlertDecoratorTest {

    static class MockAlert implements AlertComponent {
        @Override
        public String getDetails() {
            return "Patient ID: 1 | Condition: Critical Heart Rate";
        }

        @Override
        public String getPatientId() {
            return "1";
        }

        @Override
        public String getCondition() {
            return "Critical Heart Rate";
        }

        @Override
        public long getTimestamp() {
            return 1234567890L;
        }
    }

    @Test
    void testRepeatedAlertDetails() {
        AlertComponent base = new MockAlert();
        RepeatedAlertDecorator repeated = new RepeatedAlertDecorator(base, 3);

        String expected = "Patient ID: 1 | Condition: Critical Heart Rate [Repeated x3]";
        assertEquals(expected, repeated.getDetails());
    }
}
