import com.Main.DataStorageInstance;
import com.Main.HealthDataSimulatorInstance;
import com.cardio_generator.HealthDataSimulator;
import com.data_management.CholesterolReader;
import com.data_management.DataStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class SingletonTest {

    @BeforeEach
    public void setUp() {
        DataStorageInstance.reset();
        HealthDataSimulatorInstance.reset();
    }

    @Test
    public void testInstanceDataStorage() {
        DataStorageInstance.setReader(mock(CholesterolReader.class));
        DataStorage instance1 = DataStorageInstance.getInstance();
        DataStorage instance2 = DataStorageInstance.getInstance();

        DataStorage instance3 = new DataStorage(mock(CholesterolReader.class));

        assertNotNull(DataStorageInstance.getReader());
        assertEquals(instance1, instance2);
        assertNotEquals(instance1, instance3);
    }

    @Test
    public void testInstanceDataStorageWithDifferentReader() {
        DataStorageInstance.setReader(mock(CholesterolReader.class));
        DataStorage instance1 = DataStorageInstance.getInstance();

        DataStorageInstance.setReader(mock(CholesterolReader.class));
        DataStorage instance2 = DataStorageInstance.getInstance();

        assertEquals(instance1, instance2);
    }

    @Test
    public void testInstanceReset() {
        DataStorageInstance.setReader(mock(CholesterolReader.class));
        DataStorage instance1 = DataStorageInstance.getInstance();

        DataStorageInstance.reset();
        DataStorageInstance.setReader(mock(CholesterolReader.class));
        DataStorage instance2 = DataStorageInstance.getInstance();

        assertNotEquals(instance1, instance2);
    }

    @Test
    public void testInstanceDataStorageWithoutReader() {
        DataStorage instance1 = DataStorageInstance.getInstance();
        DataStorage instance2 = DataStorageInstance.getInstance();

        assertNull(DataStorageInstance.getReader());
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

    @Test
    public void testInstanceHealthDataSimulatorReset() {
        HealthDataSimulator instance1 = HealthDataSimulatorInstance.getInstance();

        HealthDataSimulatorInstance.reset();
        HealthDataSimulator instance2 = HealthDataSimulatorInstance.getInstance();

        assertNotEquals(instance1, instance2);
    }
}
