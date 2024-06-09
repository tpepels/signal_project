//package PatternsTest;
//
//import com.data_management.Patient;
//import com.data_management.PatientRecord;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.List;
//
//class PatientTest {
//    private Patient patient;
//
//    @BeforeEach
//    void setUp() {
//        patient = new Patient();
//        // Assuming Patient has a method to add records for testing
//        patient.addRecord(new PatientRecord("Systolic", 120, System.currentTimeMillis()));
//        patient.addRecord(new PatientRecord("Diastolic", 80, System.currentTimeMillis() - 10000));
//        patient.addRecord(new PatientRecord("Systolic", 125, System.currentTimeMillis() - 20000));
//        patient.addRecord(new PatientRecord("Systolic", 122, System.currentTimeMillis() - 30000));
//        patient.addRecord(new PatientRecord("Diastolic", 82, System.currentTimeMillis() - 40000));
//    }
//
//    @Test
//    void testGetLastThreeBloodPressureWithSufficientSystolicRecords() {
//        List<PatientRecord> results = patient.getLastThreeBloodPressure("Systolic");
//        assertEquals(3, results.size());
//        assertEquals(122, results.get(0).getMeasurementValue());
//        assertEquals(125, results.get(1).getMeasurementValue());
//        assertEquals(120, results.get(2).getMeasurementValue());
//    }
//
//    @Test
//    void testGetLastThreeBloodPressureWithInsufficientRecords() {
//        List<PatientRecord> results = patient.getLastThreeBloodPressure("Diastolic");
//        assertEquals(2, results.size()); // Only two diastolic records exist
//    }
//
//    @Test
//    void testGetLastThreeBloodPressureNoneOfType() {
//        List<PatientRecord> results = patient.getLastThreeBloodPressure("HeartRate");
//        assertTrue(results.isEmpty()); // No heart rate records were added
//    }
//
//    @Test
//    void testGetLastThreeBloodPressureWithEmptyPatient() {
//        Patient emptyPatient = new Patient(); // A patient with no records
//        List<PatientRecord> results = emptyPatient.getLastThreeBloodPressure("Systolic");
//        assertTrue(results.isEmpty());
//    }
//}
