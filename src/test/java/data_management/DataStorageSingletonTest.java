package data_management;

import com.data_management.DataStorage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataStorageSingletonTest {

    @Test
    public void testSingletonInstance() {
        // Get two instances of DataStorage
        DataStorage instance1 = DataStorage.getDataStorageInstance();
        DataStorage instance2 = DataStorage.getDataStorageInstance();

        // Verify that they are the same instance
        assertSame(instance1, instance2, "Both instances should be the same");
    }

    @Test
    public void testInstanceNotNull() {
        // Get an instance of DataStorage
        DataStorage instance = DataStorage.getDataStorageInstance();

        // Verify that the instance is not null
        assertNotNull(instance, "Instance should not be null");
    }
}
