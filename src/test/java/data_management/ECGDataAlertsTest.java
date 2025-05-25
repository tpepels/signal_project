package data_management;


import com.alerts.Alert;
import com.alerts.ECGDataAlerts;
import com.data_management.Patient;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ECGDataAlertsTest {

    @Test
    void testAbnormalECGPeakTriggersAlert() {
        Patient patient = new Patient(1);
        
        patient.addRecord(1.0, "ECG", 1000L);
        patient.addRecord(1.2, "ECG", 2000L);
        patient.addRecord(0.9, "ECG", 3000L);
        patient.addRecord(1.1, "ECG", 4000L);
        patient.addRecord(1.0, "ECG", 5000L);
        
        patient.addRecord(3.0, "ECG", 6000L);

        ECGDataAlerts checker = new ECGDataAlerts();
        Alert alert = checker.check(patient);

        assertTrue(alert.getCondition().contains("Abnormal ECG peak"));
        assertEquals("1", alert.getPatientId());
    }

    @Test
    void testNoAlertWhenDataWithinThreshold() {
        Patient patient = new Patient(2);
        patient.addRecord(1.0, "ECG", 1000L);
        patient.addRecord(1.1, "ECG", 2000L);
        patient.addRecord(1.2, "ECG", 3000L);
        patient.addRecord(1.0, "ECG", 4000L);
        patient.addRecord(1.1, "ECG", 5000L);
        patient.addRecord(1.0, "ECG", 6000L); 

        ECGDataAlerts checker = new ECGDataAlerts();
        Alert alert = checker.check(patient);

        assertEquals("none", alert.getCondition());
    }

    @Test
    void testNoAlertIfLessThanWindowSizeData() {
        Patient patient = new Patient(3);
        
        patient.addRecord(1.0, "ECG", 1000L);
        patient.addRecord(1.1, "ECG", 2000L);
        patient.addRecord(1.2, "ECG", 3000L);

        ECGDataAlerts checker = new ECGDataAlerts();
        Alert alert = checker.check(patient);

        assertEquals("none", alert.getCondition());
    }
}
