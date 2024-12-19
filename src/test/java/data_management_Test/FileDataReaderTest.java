package data_management_Test;

import com.data_management.DataStorage;
import com.data_management.FileDataReader;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileDataReaderTest {
    private static final String TEST_FILE_PATH = "test_data.txt";
    private DataStorage dataStorage;
    private FileDataReader fileDataReader;

    @BeforeEach
    public void setUp() throws IOException {
        DataStorage.resetInstance();
        dataStorage = DataStorage.getInstance();
        createTestFile();
        fileDataReader = new FileDataReader(TEST_FILE_PATH);
    }

    @Test
    public void testReadData() throws IOException {
        fileDataReader.readData(dataStorage);
        List<Patient> patients = dataStorage.getAllPatients();

        assertEquals(2, patients.size());

        Patient patient1 = patients.get(0);
        Patient patient2 = patients.get(1);

        List<PatientRecord> records1 = patient1.getRecords(1622470420000L, 1622470420000L);
        List<PatientRecord> records2 = patient2.getRecords(1622470520000L, 1622470520000L);

        assertEquals(1, records1.size());
        assertEquals(1, records2.size());

        PatientRecord record1 = records1.get(0);
        PatientRecord record2 = records2.get(0);

        assertEquals(1, record1.getPatientId());
        assertEquals(98.6, record1.getMeasurementValue(), 0.01);
        assertEquals("HeartRate", record1.getRecordType());
        assertEquals(1622470420000L, record1.getTimestamp());

        assertEquals(2, record2.getPatientId());
        assertEquals(120.0, record2.getMeasurementValue(), 0.01);
        assertEquals("BloodPressure", record2.getRecordType());
        assertEquals(1622470520000L, record2.getTimestamp());
    }

    private void createTestFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
            writer.write("1,98.6,HeartRate,1622470420000");
            writer.newLine();
            writer.write("2,120.0,BloodPressure,1622470520000");
        }
    }

    @Test
    public void testInvalidLine() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH, true))) {
            writer.newLine();
            writer.write("invalid,line");
        }

        fileDataReader.readData(dataStorage);
        List<Patient> patients = dataStorage.getAllPatients();

        assertEquals(2, patients.size()); // Only valid lines should be processed
    }
}
