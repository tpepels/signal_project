package data_management;

import com.datamanagement.PatientRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientRecordTest {

    @Test
    void testAllMethods() {
        PatientRecord patientRecord = new PatientRecord(1, 100.0, "WhiteBloodCells", 1714376789050L);

        int patientId = patientRecord.getPatientId();
        double measurementValue = Double.parseDouble(patientRecord.getMeasurementValue().toString());
        String recordType = patientRecord.getRecordType();
        long timestamp = patientRecord.getTimestamp();

        assertEquals(1, patientId);
        assertEquals(100.0, measurementValue);
        assertEquals("WhiteBloodCells", recordType);
        assertEquals(1714376789050L, timestamp);
    }   

}
