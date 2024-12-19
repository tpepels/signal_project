package data_management;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.FileDataReader;
import com.data_management.PatientRecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class DataStorageTest {
    private File tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = new File(System.getProperty("java.io.tmpdir"), "data_management_test");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        // Create a test file with some data
        File testFile = new File(tempDir, "test_data.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("1,100.0,WhiteBloodCells,1714376789050\n");
            writer.write("1,200.0,WhiteBloodCells,1714376789051\n");
        }
    }

    @AfterEach
    void tearDown() {
        for (File file : tempDir.listFiles()) {
            file.delete();
        }
        tempDir.delete();
    }

    @Test
    void testFileDataReader() throws IOException {
        DataStorage storage = new DataStorage();
        FileDataReader reader = new FileDataReader(tempDir.getAbsolutePath());
        reader.readData(storage);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
        assertEquals(200.0, records.get(1).getMeasurementValue()); // Validate second record
    }
}
