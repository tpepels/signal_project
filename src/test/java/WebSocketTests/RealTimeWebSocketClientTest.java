package WebSocketTests;

import static org.mockito.Mockito.*;

import com.cardio_generator.outputs.RealTimeWebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.data_management.DataStorage;
import java.net.URI;

public class RealTimeWebSocketClientTest {

    private RealTimeWebSocketClient client;
    private ServerHandshake mockHandshake;
    private DataStorage mockDataStorage;

    @BeforeEach
    public void setUp() throws Exception {
        mockDataStorage = mock(DataStorage.class);
        URI serverUri = new URI("ws://localhost:8080");
        client = new RealTimeWebSocketClient(serverUri, mockDataStorage);
        mockHandshake = mock(ServerHandshake.class);
    }

    @Test
    public void testOnOpen() {
        client.onOpen(mockHandshake);
        // Optionally verify that some state has changed or a message has been logged
        System.out.println("Connected to WebSocket server as expected");
    }

    @Test
    public void testOnMessage_invalidData() {
        String message = "invalid data structure"; // Deliberately incorrect format
        client.onMessage(message);
        // Expect that no data is stored and an error is logged or handled
        verify(mockDataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

}
