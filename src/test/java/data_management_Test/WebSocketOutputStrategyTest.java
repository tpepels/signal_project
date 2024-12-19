package data_management_Test;
import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cardio_generator.outputs.WebSocketOutputStrategy;

import java.util.Collections;
import static org.mockito.Mockito.*;

public class WebSocketOutputStrategyTest {
    private WebSocketOutputStrategy outputStrategy;
    private WebSocketServer server;
    private WebSocket mockConnection;
    @BeforeEach
    void setUp() throws Exception {
        server = mock(WebSocketServer.class);
        outputStrategy = new WebSocketOutputStrategy(8080);
        mockConnection = mock(WebSocket.class);
        // Using reflection to set the server field
        java.lang.reflect.Field serverField = WebSocketOutputStrategy.class.getDeclaredField("server");
        serverField.setAccessible(true);
        serverField.set(outputStrategy, server);
        when(server.getConnections()).thenReturn(Collections.singleton(mockConnection));
    }

    @Test
    void testOutputValidData() {
        outputStrategy.output(1, 1627842123000L, "HeartRate", "78.0");
        verify(mockConnection, times(1)).send("1,1627842123000,HeartRate,78.0");
    }

}
