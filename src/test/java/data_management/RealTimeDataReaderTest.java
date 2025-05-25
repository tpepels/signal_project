package data_management;

import org.junit.jupiter.api.Test;
import com.data_management.RealTimeDataReader;
import static org.junit.jupiter.api.Assertions.*;

public class RealTimeDataReaderTest {

    @Test
    public void testStartStopReading() {
        assertDoesNotThrow(() -> {
            RealTimeDataReader reader = new RealTimeDataReader("ws://localhost:8887");
            reader.startReading();  
            Thread.sleep(2000);    
            reader.stopReading();
        });
    }
}