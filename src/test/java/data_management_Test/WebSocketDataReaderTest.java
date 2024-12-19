package data_management_Test;
import com.data_management.WebSocketDataReader;
import com.data_management.DataStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
public class WebSocketDataReaderTest {
    private WebSocketDataReader dataReader;
    private DataStorage dataStorage;
    @BeforeEach
    void setUp() throws URISyntaxException {
        dataStorage = mock(DataStorage.class);
        dataReader = new WebSocketDataReader("ws://localhost:8080", dataStorage);
    }
    @Test
    void testProcessMessage() {
        String validMessage = "1,1627842123000,HeartRate,78.0";
        dataReader.processMessage(validMessage);
        verify(dataStorage, times(1)).addPatientData(1, 78.0, "HeartRate", 1627842123000L);
    }

    @Test
    void testProcessMessageInvalidData() {
        String invalidMessage = "invalid,message";
        dataReader.processMessage(invalidMessage);
        verify(dataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }



    @Test
    void testProcessMessageInvalidTimestamp() {
        String invalidTimestampMessage = "1,notatimestamp,HeartRate,78.0";
        dataReader.processMessage(invalidTimestampMessage);
        verify(dataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }
    @Test
    void testProcessMessageInvalidPatientId() {
        String invalidPatientIdMessage = "invalid,1627842123000,HeartRate,78.0";
        dataReader.processMessage(invalidPatientIdMessage);
        verify(dataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }
    @Test
    void testProcessMessageWithNonNumericData() {
        String nonNumericDataMessage = "1,1627842123000,HeartRate,nonNumericData";
        dataReader.processMessage(nonNumericDataMessage);
        verify(dataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

}
