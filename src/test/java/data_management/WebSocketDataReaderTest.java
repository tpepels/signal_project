package data_management;

import com.cardiogenerator.outputs.WebSocketOutputStrategy;
import com.datamanagement.Client;
import com.datamanagement.DataStorage;
import com.datamanagement.PatientRecord;
import com.datamanagement.WebSocketDataReader;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;

public class WebSocketDataReaderTest {

    @Test
    void testReadData() throws IOException {

        WebSocketOutputStrategy strategy = new WebSocketOutputStrategy(8080);
        WebSocketDataReader reader = new WebSocketDataReader("ws://localhost:8080");
        DataStorage storage = DataStorage.getInstance();

        reader.readData(storage);
    }
}
