package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

class FileDataReaderTest {

    @Test
    void testFileDataReaderReadsCorrectly() throws Exception {
        
        File tempDir = new File("temp_test_data");
        tempDir.mkdir();
        File testFile = new File(tempDir, "HeartRate.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("Patient ID: 1, Timestamp: 1714376789050, Label: HeartRate, Data: 72.5\n");
            writer.write("Patient ID: 1, Timestamp: 1714376789051, Label: HeartRate, Data: 75.0\n");
        }

        
        FileDataReader reader = new FileDataReader(tempDir.getAbsolutePath());
        DataStorage storage = new DataStorage();
        reader.readData(storage);

        
        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789052L);
        assertEquals(2, records.size());
        assertEquals(72.5, records.get(0).getMeasurementValue(), 0.001);
        assertEquals(75.0, records.get(1).getMeasurementValue(), 0.001);

        
        assertTrue(testFile.delete());
        assertTrue(tempDir.delete());
    }
}
