package data_management;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.data_management.DataReader;
import com.data_management.DataStorage;

public class DataReaderTest{

    @Test
    public void testReadDataAddsRecordsToStorage() throws IOException {
        DataStorage storage = new DataStorage();
        DataReader reader = new MockDataReader();

        reader.readData(storage);

        var records = storage.getRecords(1, 1714376788999L, 1714376789060L);
        assertEquals(2, records.size());
        assertEquals("Temperature", records.get(0).getRecordType());
        assertEquals("HeartRate", records.get(1).getRecordType());
    }
}