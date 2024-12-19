package data_management_Test;

import com.data_management.DataStorage;
import com.data_management.WebSocketClientImpl;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class WebSocketClientTest {
    private DataStorage dataStorage;
    private WebSocketClientImpl webSocketClient;

    @BeforeEach
    public void setUp() throws URISyntaxException {
        DataStorage.resetInstance();
        dataStorage = DataStorage.getInstance();
        webSocketClient = new WebSocketClientImpl("ws://localhost:8080", dataStorage);
    }

    @Test
    public void testOnMessageMalformedData() {
        String malformedMessage = "1,notatimestamp,HeartRate,78.0";
        webSocketClient.onMessage(malformedMessage);
        // Verify no data was added
        assertTrue(dataStorage.getAllPatients().isEmpty());
    }

    @Test
    public void testOnMessageValidData() {
        String validMessage = "1,1622470420000,HeartRate,78.0";
        webSocketClient.onMessage(validMessage);
        // Verify data was added
        assertFalse(dataStorage.getAllPatients().isEmpty());
    }

    @Test
    public void testOnError() {
        Exception testException = new Exception("Test exception");
        // Simulate an error and verify the error handling
        try {
            webSocketClient.onError(testException);
        } catch (Exception e) {
            fail("Exception should be handled within the onError method");
        }
    }

    @Test
    public void testOnOpen() {
        ServerHandshake serverHandshake = new ServerHandshake() {
            @Override
            public Iterator<String> iterateHttpFields() {
                return null;
            }

            @Override
            public String getFieldValue(String s) {
                return null;
            }

            @Override
            public boolean hasFieldValue(String s) {
                return false;
            }

            @Override
            public byte[] getContent() {
                return new byte[0];
            }

            @Override
            public short getHttpStatus() {
                return 0;
            }

            @Override
            public String getHttpStatusMessage() {
                return null;
            }
            // Implement methods as needed for the test
        };
        webSocketClient.onOpen(serverHandshake);
        // No assertion needed, just ensuring no exceptions are thrown
    }

    @Test
    public void testOnClose() {
        webSocketClient.onClose(1000, "Normal closure", true);
        // No assertion needed, just ensuring no exceptions are thrown
    }
}
