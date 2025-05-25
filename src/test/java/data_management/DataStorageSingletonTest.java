package data_management;

import com.data_management.DataStorageSingleton;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class DataStorageSingletonTest {

    @BeforeEach
    public void clearStorage() {
        DataStorageSingleton.getInstance().clear();
    }

    @Test
    public void testNoDuplicateRecords() {
        DataStorageSingleton storage = DataStorageSingleton.getInstance();
        int patientId = 1;
        long timestamp = System.currentTimeMillis();

        storage.addPatientData(patientId, 120, "SystolicPressure", timestamp);
        storage.addPatientData(patientId, 120, "SystolicPressure", timestamp); //duplicate

        long now = System.currentTimeMillis();
        int size = storage.getRecords(patientId, timestamp, now).size();

        assertEquals(1, size);
    }
}
