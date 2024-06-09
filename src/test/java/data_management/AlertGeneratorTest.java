//import com.alerts.AlertGenerator;
//import com.data_management.DataStorage;
//import com.data_management.Patient;
//import com.data_management.PatientRecord;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class AlertGeneratorTest {
//    private AlertGenerator alertGenerator = new AlertGenerator(new DataStorage());
//
//    @Test
//    void testBloodPressureIncreasingTrendAlert() {
//        PatientRecord record1 = new PatientRecord(1, 130, "Systolic", System.currentTimeMillis() - 60000);
//        PatientRecord record2 = new PatientRecord(1, 141, "Systolic", System.currentTimeMillis() - 30000);
//        PatientRecord record3 = new PatientRecord(1, 153, "Systolic", System.currentTimeMillis());
//        assertTrue(alertGenerator.checkBloodPressure(record1, record2, record3, "Systolic"));
//    }
//
//    @Test
//    void testBloodPressureDecreasingTrendAlert() {
//        PatientRecord record1 = new PatientRecord(1, 100, "Diastolic", System.currentTimeMillis() - 60000);
//        PatientRecord record2 = new PatientRecord(1, 89, "Diastolic", System.currentTimeMillis() - 30000);
//        PatientRecord record3 = new PatientRecord(1, 78, "Diastolic", System.currentTimeMillis());
//        assertTrue(alertGenerator.checkBloodPressure(record1, record2, record3, "Diastolic"));
//    }
//
//    @Test
//    void testBloodPressureCriticalThresholdAlert() {
//        PatientRecord record = new PatientRecord(1, 185, "Systolic", System.currentTimeMillis());
//        assertTrue(alertGenerator.checkBloodPressure(record, record, record, "Systolic"));
//    }
//
//    @Test
//    void testLowSaturationAlert() {
//        PatientRecord record = new PatientRecord(1, 90, "Saturation", System.currentTimeMillis());
//        assertTrue(alertGenerator.checkBloodSaturation(record, record, null));
//    }
//
//    @Test
//    void testRapidDropSaturationAlert() {
//        PatientRecord recordStart = new PatientRecord(1, 97, "Saturation", System.currentTimeMillis() - 600000);
//        PatientRecord recordEnd = new PatientRecord(1, 91, "Saturation", System.currentTimeMillis());
//        assertTrue(alertGenerator.checkBloodSaturation(recordStart, recordEnd, null));
//    }
//
//    @Test
//    void testCombinedAlertCondition() {
//        PatientRecord recordSystolic = new PatientRecord(1, 85, "Systolic", System.currentTimeMillis());
//        PatientRecord recordSaturation = new PatientRecord(1, 90, "Saturation", System.currentTimeMillis());
//        assertTrue(alertGenerator.checkCombinedAlert(recordSystolic, recordSaturation));
//    }
//
//    @Test
//    void testAbnormalECGHeartRateAlert() {
//        PatientRecord record = new PatientRecord(1, 45, "ECG", System.currentTimeMillis());
//        List<PatientRecord> lastTenMinutesRecords = new ArrayList<>();
//        assertTrue(alertGenerator.checkECGAlert(record, lastTenMinutesRecords));
//    }
//
//    @Test
//    void testIrregularECGPatternAlert() {
//        PatientRecord recordLast = new PatientRecord(1, 100, "ECG", System.currentTimeMillis());
//        PatientRecord recordPrevious = new PatientRecord(1, 80, "ECG", System.currentTimeMillis() - 30000);
//        List<PatientRecord> lastTenMinutesRecords = List.of(recordPrevious, recordLast);
//        assertTrue(alertGenerator.checkECGAlert(recordLast, lastTenMinutesRecords));
//    }
//
//    @Test
//    void testManuallyTriggeredAlert(){
//        PatientRecord recordLast = new PatientRecord(1,1.0,"Alert", System.currentTimeMillis());
//        assertTrue(alertGenerator.checkManuallyTriggered(recordLast));
//    }
//
//}
