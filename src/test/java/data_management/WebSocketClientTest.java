package data_management;

import static org.mockito.Mockito.*;

import com.data_management.DataStorage;
import com.data_management.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.net.URI;

class WebSocketClientTest {

    private WebSocketClient client;
    private DataStorage mockStorage;

    @BeforeEach
    void setUp() {
        URI dummyUri = URI.create("ws://dummyuri");
        mockStorage = mock(DataStorage.class);
        client = new WebSocketClient(dummyUri, mockStorage);
    }

    @Test
    void testOnMessageWithValidData() {
        String validMessage = "123,1609459200,XYZ,45%";
        client.onMessage(validMessage);
        verify(mockStorage).addPatientData(123, 45.0, "XYZ", 1609459200);
    }

    @Test
    void testOnMessageWithInvalidData() {
        String invalidMessage = "abc,xyz,uh,oh%";
        client.onMessage(invalidMessage);
        verify(mockStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

    @Test
    void testOnMessageWithInvalidPercentage() {
        String invalidMessage = "123,1609459200,XYZ,45";
        client.onMessage(invalidMessage);
        verify(mockStorage).addPatientData(123, 45.0, "XYZ", 1609459200);
    }

    @Test
    void testOnMessageWithInvalidDataLength() {
        String invalidMessage = "123,1609459200,XYZ,45%,extra";
        client.onMessage(invalidMessage);
        verify(mockStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

    @Test
    void testOnClose() {
        client.onClose(1000, "Normal closure", true);
        verify(mockStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

    @Test
    void testOnError() {
        client.onError(new Exception("Test exception"));
        verify(mockStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

    @Test
    void testOnOpen() {
        client.onOpen(mock(ServerHandshake.class));
        verify(mockStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }
}
