package WebSocketTests;

import static org.mockito.Mockito.*;

import com.cardio_generator.outputs.RealTimeWebSocketClient;
import com.cardio_generator.outputs.WebSocketOutputStrategy;
import com.data_management.DataStorage;
import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collection;

public class WebSocketOutputStrategyTest {

    private WebSocketOutputStrategy strategy;
    private WebSocketServer mockServer;
    private RealTimeWebSocketClient mockClient;
    private DataStorage mockDataStorage;

    @BeforeEach
    public void setUp() {
        mockServer = mock(WebSocketServer.class);
        mockClient = mock(RealTimeWebSocketClient.class);
        mockDataStorage = mock(DataStorage.class);

        strategy = new WebSocketOutputStrategy(mockServer, mockClient, mockDataStorage);
    }

    @Test
    public void testBroadcastMessage() {
        String testMessage = "1,162025,ECG,120";
        Collection<WebSocket> mockConnections = new ArrayList<>();
        WebSocket mockWebSocket = mock(WebSocket.class);
        mockConnections.add(mockWebSocket);

        when(mockServer.getConnections()).thenReturn(mockConnections);

        strategy.output(1, 162025L, "ECG", "120");

        verify(mockWebSocket, times(1)).send(testMessage);
    }
}