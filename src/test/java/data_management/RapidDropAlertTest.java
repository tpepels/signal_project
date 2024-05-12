package data_management;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.alerts.Alert;
import com.alerts.RapidDropAlert;
import com.data_management.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class RapidDropAlertTest {
    private Patient patient;
    private long now;
    private RapidDropAlert alert;

    @BeforeEach
    public void setUp() {
        patient = new Patient(1); // Assuming constructor sets up an empty list of records
        now = System.currentTimeMillis();
        alert = new RapidDropAlert();
    }

    @Test
    public void testNoRapidDrop() {
        patient.addRecord(95, "Saturation", now);
        patient.addRecord(94, "Saturation", now + 200000); // 3.33 minutes later

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(0, alerts.size(), "Should not generate any alerts as there is no significant drop");
    }

    @Test
    public void testRapidDropAtLimit() {
        long now = System.currentTimeMillis();
        patient.addRecord(95, "Saturation", now);
        patient.addRecord(90, "Saturation", now + 299999); // Just under 5 minutes

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(1, alerts.size(), "Should generate one alert for exactly 5 units drop within 5 minutes");
    }

    @Test
    public void testRapidDropOverLongerPeriod() {
        patient.addRecord(95, "Saturation", now);
        patient.addRecord(88, "Saturation", now + 600001); // Slightly over 10 minutes

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(0, alerts.size(), "Should not generate any alerts as the drop occurs over too long a period");
    }

    @Test
    public void testMultipleRapidDrops() {
        patient.addRecord(95, "Saturation", now);
        patient.addRecord(89, "Saturation", now + 100000); // Just over 1.66 minutes later
        patient.addRecord(83, "Saturation", now + 200000); // Another drop

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(2, alerts.size(), "Should generate two alerts for two separate significant drops");
    }

    @Test
    public void testImmediateRapidDrop() {
        patient.addRecord(95, "Saturation", now);
        patient.addRecord(85, "Saturation", now + 50000); // 50 seconds later

        List<Alert> alerts = alert.checkCondition(patient);
        assertEquals(1, alerts.size(), "Should generate one alert for rapid drop within a very short time");
    }
}

