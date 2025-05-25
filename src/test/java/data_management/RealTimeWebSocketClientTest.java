package data_management;

import com.webSocket.RealTimeWebSocketClient;
import com.data_management.DataStorageSingleton;
import com.data_management.PatientRecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RealTimeWebSocketClientTest {

    @BeforeEach
    public void clearStorage() {
        DataStorageSingleton.getInstance().clear();
    }

    @Test
    public void testConnectionFailure() throws Exception {
        URI badUri = URI.create("ws://invalid_host:12345");
        RealTimeWebSocketClient client = new RealTimeWebSocketClient(badUri);
            
        

        boolean connected = client.connectBlocking();
        assertFalse(connected, "Expected connection to fail, but it succeeded.");
        
    }

    @Test
    public void testUnexpectedDisconnection() throws Exception {
        URI uri = new URI("ws://localhost:8887");
        RealTimeWebSocketClient client = new RealTimeWebSocketClient(uri);

        client.onClose(1006, "Simulated network error", true);
    }

    @Test
    public void testOnErrorHandling() {
        RealTimeWebSocketClient client = new RealTimeWebSocketClient(URI.create("ws://localhost:8887"));
        assertDoesNotThrow(() -> client.onError(new Exception("Simulated error")));
    }

    @Test
    public void testValidJsonMessage() throws Exception {
        String jsonMessage = "{\"patientId\": 1, \"measurementValue\": 120, \"recordType\": \"SystolicPressure\", \"timestamp\": 1716123910000}";

        RealTimeWebSocketClient client = new RealTimeWebSocketClient(new URI("ws://localhost:8887"));
        client.onMessage(jsonMessage);

        List<PatientRecord> records = DataStorageSingleton.getInstance().getRecords(1, 1716123910000L, 1716123910000L);

        assertFalse(records.isEmpty(), "No records found for patient ID 1.");
        PatientRecord record = records.get(0);
        assertEquals(120.0, record.getMeasurementValue(), 0.001);
        assertEquals("SystolicPressure", record.getRecordType());
        assertEquals(1716123910000L, record.getTimestamp());
    }

     @Test
    public void testValidECGJsonMessage() throws Exception {
        String jsonMessage = "{\"patientId\": 2, \"measurementValue\": 0.75, \"recordType\": \"ECG\", \"timestamp\": 1716123910000}";

        RealTimeWebSocketClient client = new RealTimeWebSocketClient(new URI("ws://localhost:8887"));
        client.onMessage(jsonMessage);

        List<PatientRecord> records = DataStorageSingleton.getInstance().getRecords(2, 1716123910000L, 1716123910000L);

        assertFalse(records.isEmpty(), "Expected at least one record for patient ID 2.");
        PatientRecord record = records.get(0);
        assertEquals(0.75, record.getMeasurementValue(), 0.001);
        assertEquals("ECG", record.getRecordType());
        assertEquals(1716123910000L, record.getTimestamp());
    }

     @Test
    public void testInvalidJsonMissingField() throws Exception {
        String malformedJson = "{\"patientId\": 1, \"measurementValue\": 98.6, \"recordType\": \"HeartRate\"}"; //Missing timestamp

        RealTimeWebSocketClient client = new RealTimeWebSocketClient(new URI("ws://localhost:8887"));

        assertDoesNotThrow(() -> client.onMessage(malformedJson));
    }

    @Test
    public void testInvalidJsonWithWrongType() throws Exception {
        String invalidNumericJson = "{\"patientId\": 1, \"measurementValue\": \"not_a_number\", \"recordType\": \"HeartRate\", \"timestamp\": 1716123910000}";

        RealTimeWebSocketClient client = new RealTimeWebSocketClient(new URI("ws://localhost:8887"));

        assertDoesNotThrow(() -> client.onMessage(invalidNumericJson));
    }
}
