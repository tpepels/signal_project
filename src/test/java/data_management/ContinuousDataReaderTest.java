package data_management;
import static org.mockito.Mockito.*;

import com.data_management.ContinuousDataReader;
import com.data_management.DataStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.IOException;
import java.net.URI;

class ContinuousDataReaderTest {

    private ContinuousDataReader reader;
    private DataStorage mockDataStorage;

    @BeforeEach
    void setUp() {
        reader = new ContinuousDataReader();
        mockDataStorage = mock(DataStorage.class);
        URI mockUri = URI.create("ws://testserver");
        reader.setServerUri(mockUri);
    }

    @Test
    void testReadDataInitializesWebSocketClientCorrectly() throws IOException {
        reader.readData(mockDataStorage);
    }

    @Test
    void testSetServerUriUpdatesUriCorrectly() throws IOException {
        URI newUri = URI.create("ws://newtestserver");
        reader.setServerUri(newUri);
        reader.readData(mockDataStorage);
    }

    @Test
    void testReadDataWithInvalidUri() throws IOException {
        URI invalidUri = URI.create("invalid");
        reader.setServerUri(invalidUri);
        reader.readData(mockDataStorage);
    }

    @Test
    void testReadDataWithNullDataStorage() throws IOException {
        reader.readData(null);
    }

    @Test
    void testReadDataWithIOException() throws IOException {
        URI mockUri = URI.create("ws://exception");
        reader.setServerUri(mockUri);
        reader.readData(mockDataStorage);
    }
}
