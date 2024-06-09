package data_management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.alerts.Alert;
import com.alerts.AbnormalDataAlert;
import com.data_management.Patient;

public class AbnormalDataAlertTest {
    
    @Test
    public void ECGDataIsAbnormalTest() {
        Patient patient = new Patient(1);
        patient.addRecord(-1, "ECG", 1000);
        patient.addRecord(-0.75, "ECG", 1001);
        patient.addRecord(0.1, "ECG", 1002);
        patient.addRecord(-1, "ECG", 1003);
        patient.addRecord(-1, "ECG", 1004);

        AbnormalDataAlert alert = new AbnormalDataAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals("One Alert should be generated", 1, alerts.size());
    }

    @Test
    public void ECGDataIsNormalTests() {
        Patient patient = new Patient(1);
        patient.addRecord(0, "ECG", 1000);

        AbnormalDataAlert alert = new AbnormalDataAlert();

        List<Alert> alerts = alert.checkCondition(patient);
        assertTrue("No alerts should be generated", alerts.isEmpty());
    }
}
