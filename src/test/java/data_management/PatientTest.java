package data_management;

import com.datamanagement.Patient;
import com.datamanagement.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientTest {

    @Test
    void testAddAndGetRecords() {
        Patient patient = new Patient(1);
        patient.addRecord(100.0, "WhiteBloodCells", 1714376789050L);
        patient.addRecord(200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = patient.getRecords(1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
    }

}
