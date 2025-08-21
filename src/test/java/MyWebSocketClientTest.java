import com.data_management.DataStorage;
import com.data_management.MyWebSocketClient;
import com.data_management.PatientRecord;
import org.awaitility.Awaitility;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class MyWebSocketClientTest {

    private WebSocketServer inlineServer;
    private MyWebSocketClient client;
    private DataStorage storage;

    @BeforeEach
    void setUp() throws Exception {
        // Inline minimal WebSocket server on port 12345
        inlineServer = new WebSocketServer(new InetSocketAddress(12345)) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                System.out.println("Client connected");
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                System.out.println("Client disconnected");
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                // Not needed for test
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                ex.printStackTrace();
            }

            @Override
            public void onStart() {
                System.out.println("Inline WebSocket server started");
            }
        };
        inlineServer.start();
        Thread.sleep(1000); // Give it time to bind the port

        storage = DataStorage.getInstance();
        storage.getAllPatients().clear();

        client = new MyWebSocketClient(new URI("ws://localhost:12345"), storage);
        client.connectBlocking();
    }

    @AfterEach
    void tearDown() throws Exception {
        if (client != null && client.isOpen()) {
            client.close();
        }
        if (inlineServer != null) {
            inlineServer.stop();
        }
    }

    @Test
    void testClientReceivesAndStoresMessage() throws Exception {
        assertTrue(client.isOpen(), "Client should be connected");

        // Simulate server message
        String testMessage = "123|85.0|HeartRate|1609459200000";
        for (WebSocket conn : inlineServer.getConnections()) {
            conn.send(testMessage);
        }

        Awaitility.await().atMost(2, TimeUnit.SECONDS).until(() ->
                !storage.getRecords(123, 1609459190000L, 1609459210000L).isEmpty()
        );

        List<PatientRecord> records = storage.getRecords(123, 1609459190000L, 1609459210000L);
        assertEquals(1, records.size());

        PatientRecord record = records.get(0);
        assertEquals(123, record.getPatientId());
        assertEquals(85.0, record.getMeasurementValue());
        assertEquals("HeartRate", record.getRecordType());
        assertEquals(1609459200000L, record.getTimestamp());
    }
}
