package data_management;

import com.alerts.alert_decorators.PriorityAlertDecorator;
import com.alerts.alert_factories.BloodPressureAlertFactory;
import com.alerts.alert_strategies.BloodPressureStrategy;
import com.alerts.alerts.Alert;
import com.alerts.alerts.BloodPressureAlert;
import com.datamanagement.Patient;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PriorityAlertTest {
    @Test
    void testPriorityAlert() {
        BloodPressureStrategy rule = new BloodPressureStrategy();
        Patient p4 = new Patient(4);
        p4.addRecord(49.0, "DiastolicPressure", 1L);
        List<Alert> alertList = rule.checkAlert(p4.getPatientId(), p4.getPatientRecords());
        List<Alert> alertList2 = rule.checkAlert(p4.getPatientId(), p4.getPatientRecords());
        for (Alert alert : alertList2) {
            PriorityAlertDecorator decorator = new PriorityAlertDecorator(alert);
            decorator.prioritizeAlert();
        }
        assertEquals(alertList2.size(), alertList.size());
        for (int i = 0; i < alertList2.size(); i++) {
            String condition = alertList.get(i).getCondition();
            String condition2 = alertList2.get(i).getCondition();
            int strLength = condition.length();
            int strLength2 = condition2.length();
            assertTrue(strLength2 > strLength);
            assertTrue(condition2.contains("PRIORITY: "));
            assertEquals(condition2.replaceAll("PRIORITY: ", ""), condition);
        }


    }
}
