package data_management;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.data_management.Patient;
import com.data_management.PatientRecord;

class PateintTest {
    // let's see
    @Test
    void testAddAndGetRecords() 
    {
        
        // TODO Perhaps you can implement a mock data reader to mock the test data?
        // DataReader reader
        Patient dataPatient = new Patient( 1);
        dataPatient.addRecord(20,"Heart Rate" ,1714376789050L);
        

        List<PatientRecord> records = dataPatient.getRecords(1714376789049L,  1714376789051L);
        assertEquals(1, records.size()); // Check if two records are retrieved
        assertEquals(20.0, records.get(0).getMeasurementValue()); // Validate first record
    }
}