package data_management;

import com.datamanagement.Client;
import com.datamanagement.DataStorage;
import com.datamanagement.PatientRecord;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

public class ClientTest {

    @Test
    void testOnMessage() {
        
        try {
            URI uri = new URI("ws://localhost:8080");
            DataStorage storage = DataStorage.getInstance();
            
            Client client = new Client(uri, storage);

            client.onMessage("1,50000,ECG,78.9");

            PatientRecord record = storage.getRecords(1, 50000, 50000).get(0);
            
            assertEquals(1, record.getPatientId());
            assertEquals(50000, record.getTimestamp());
            assertEquals("ECG", record.getRecordType());
            assertEquals("78.9", record.getMeasurementValue());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
