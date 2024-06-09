package data_management;

import com.cardio_generator.HealthDataSimulator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HealthDataSimulatorSingletonTest {

    @Test
    public void testSingletonInstance() {
        // Get two instances of HealthDataSimulator
        HealthDataSimulator instance1 = HealthDataSimulator.getInstance();
        HealthDataSimulator instance2 = HealthDataSimulator.getInstance();

        // Verify that they are the same instance
        assertSame(instance1, instance2, "Both instances should be the same");
    }

    @Test
    public void testInstanceNotNull() {
        // Get an instance of HealthDataSimulator
        HealthDataSimulator instance = HealthDataSimulator.getInstance();

        // Verify that the instance is not null
        assertNotNull(instance, "Instance should not be null");
    }
}
