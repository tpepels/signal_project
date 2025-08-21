
import com.data_management.DataStorage;
import com.data_management.MyWebSocketClient;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.alerts.AlertGenerator;
import com.alerts.AlertStrategy;
import com.alerts.Alert;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.junit.jupiter.api.*;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.*;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class WebSocketDataIntegrationTest {

    private static final int PORT = 8899;
    private static TestWebSocketServer server;
    private DataStorage dataStorage;
    private MyWebSocketClient client;

    @BeforeAll
    static void startServer() throws Exception {
        server = new TestWebSocketServer(new InetSocketAddress(PORT));
        server.start();
        Thread.sleep(500); // Wait for server to start
    }

    @AfterAll
    static void stopServer() throws Exception {
        server.stop(1000);
    }

    @BeforeEach
    void setUp() throws Exception {
        dataStorage = DataStorage.getInstance();
        dataStorage.clear(); // Ensure clean state
        client = new MyWebSocketClient(new URI("ws://localhost:" + PORT), dataStorage);
        client.connectBlocking();
    }

    @Test
    void testRealTimeDataProcessingAndAlertGeneration() throws Exception {
        // Prepare a latch to wait for the client to process the message
        CountDownLatch latch = new CountDownLatch(1);

        // Simulate a simple alert strategy: triggers if HeartRate > 100
        List<AlertStrategy> strategies = new ArrayList<>();
        strategies.add(new AlertStrategy() {
            @Override
            public boolean checkAlert(List<PatientRecord> records) {
                return records.stream().anyMatch(r -> r.getRecordType().equals("HeartRate") && r.getMeasurementValue() > 100);
            }
            @Override
            public String getCondition() {
                return "HighHeartRate";
            }
        });

        List<Alert> triggeredAlerts = new ArrayList<>();
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage, strategies, triggeredAlerts);

        // Compose a valid message: patientId|value|type|timestamp
        String message = "123|120.5|HeartRate|" + System.currentTimeMillis();

        // Send the message from the server to the client
        server.broadcast(message);

        // Wait for the client to process the message
        Thread.sleep(500);

        // Check that the data was stored
        List<PatientRecord> records = dataStorage.getRecords(123, 0, System.currentTimeMillis());
        assertFalse(records.isEmpty(), "Patient records should not be empty after receiving data");
        PatientRecord rec = records.get(0);
        assertEquals(123, rec.getPatientId());
        assertEquals(120.5, rec.getMeasurementValue());
        assertEquals("HeartRate", rec.getRecordType());

        // Evaluate alerts
        Patient patient = dataStorage.getAllPatients().stream()
                .filter(p -> p.getPatientId() == 123)
                .findFirst()
                .orElse(null);
        assertNotNull(patient, "Patient should exist in storage");

        alertGenerator.evaluateData(patient);

        // Check that an alert was triggered
        assertFalse(triggeredAlerts.isEmpty(), "An alert should have been triggered");
        Alert alert = triggeredAlerts.get(0);
        assertEquals("123", alert.getPatientId());
        assertEquals("HighHeartRate", alert.getCondition());
    }

    // Simple embedded WebSocket server for integration testing
    private static class TestWebSocketServer extends WebSocketServer {
        public TestWebSocketServer(InetSocketAddress address) {
            super(address);
        }
        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {}
        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {}
        @Override
        public void onMessage(WebSocket conn, String message) {}
        @Override
        public void onError(WebSocket conn, Exception ex) {}
        @Override
        public void onStart() {}
    }
}
