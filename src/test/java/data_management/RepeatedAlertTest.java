package data_management;

import com.alerts.alert_decorators.RepeatedAlertDecorator;
import com.alerts.alert_strategies.AlertStrategy;
import com.alerts.alert_strategies.BloodPressureStrategy;
import com.alerts.alerts.Alert;
import com.datamanagement.Patient;
import com.datamanagement.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RepeatedAlertTest {
    @Test
    public void testRepeatedAlert() {
        Patient p1 = new Patient(1);
        p1.addRecord(49.0, "DiastolicPressure", 1L);
        p1.addRecord(50.0, "DiastolicPressure", 2L);
        p1.addRecord(51.0, "DiastolicPressure", 3L);
        List<PatientRecord> pr = p1.getPatientRecords();
        AlertStrategy as = new BloodPressureStrategy();
        List<Alert> alerts1 = as.checkAlert(p1.getPatientId(), pr);
        RepeatedAlertDecorator repeat = new RepeatedAlertDecorator(p1, as, 1,2);
        List<Alert> alerts2 = repeat.repeatedAlerts();
        for (Alert alert : alerts2) {
            assertTrue(alert.getCondition().contains("REPEATED"));
        }
        for (Alert alert : alerts1) {
            assertFalse(alert.getCondition().contains("REPEATED"));
        }
    }
}
