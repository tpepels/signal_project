import com.Main.DataStorageInstance;
import com.Main.HealthDataSimulatorInstance;
import com.cardio_generator.HealthDataSimulator;
import com.data_management.CholesterolReader;
import com.data_management.DataStorage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class SingletonTest {

    @Test
    public void testInstanceDataStorage() {
        DataStorageInstance.setReader(mock(CholesterolReader.class));
        DataStorage instance1 = DataStorageInstance.getInstance();
        DataStorage instance2 = DataStorageInstance.getInstance();

        DataStorage instance3 = new DataStorage(mock(CholesterolReader.class));

        assertEquals(instance1, instance2);
        assertNotEquals(instance1, instance3);
    }

    @Test
    public void testInstanceDataStorageWithoutReader() {
        DataStorage instance1 = DataStorageInstance.getInstance();
        DataStorage instance2 = DataStorageInstance.getInstance();

        assertEquals(instance1, instance2);
    }

    @Test
    public void testInstanceHealthDataSimulator() {
        HealthDataSimulator instance1 = HealthDataSimulatorInstance.getInstance();
        HealthDataSimulator instance2 = HealthDataSimulatorInstance.getInstance();

        HealthDataSimulator instance3 = new HealthDataSimulator();

        assertEquals(instance1, instance2);
        assertNotEquals(instance1, instance3);
    }
}
