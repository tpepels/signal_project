package data_management;
import org.junit.jupiter.api.Test;
import com.data_management.*;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataReaderTest {

    @Test
    void testMockDataReaderAddsDataToStorage() throws IOException {
        // Arrange: create a mock DataReader
        DataReader mockReader = dataStorage -> dataStorage.addPatientData(1, 98.6, "Temperature", 1714376000000L);

        DataStorage storage = DataStorage.getInstance();

        // Act: invoke the mock reader
        mockReader.readData(storage);

        // Assert: check if the data was added
        List<PatientRecord> records = storage.getRecords(1, 1714375990000L, 1714376010000L);
        assertEquals(1, records.size());
        assertEquals(98.6, records.get(0).getMeasurementValue(), 0.001);
    }
}
