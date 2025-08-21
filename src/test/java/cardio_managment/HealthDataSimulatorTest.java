package cardio_managment;

import com.cardio_generator.HealthDataSimulator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HealthDataSimulatorTest {

    @Test
    void testSingletonInstance() {
        HealthDataSimulator instance1 = HealthDataSimulator.getInstance();
        HealthDataSimulator instance2 = HealthDataSimulator.getInstance();
        assertSame(instance1, instance2, "HealthDataSimulator should return the same instance (singleton)");
    }
}
