package alert_test;
import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import java.util.List;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static com.alerts.AlertGenerator.evaluateData;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlertGeneratorTest {
    private AlertGenerator alertGenerator;
    private List<Alert> alerts;


    @Test
    void testAbnormalHeartRateAlerts() {
        alerts = new ArrayList<Alert>();
        DataStorage storage = DataStorage.getInstance();
        AlertGenerator a = new AlertGenerator(storage, alerts);


        // Test case 1: Normal heart rate, no alerts should be triggered
        Patient p1 = new Patient(1);
        storage.addPatientData(1, 72.0, "HeartRate", 1714376789050L);
        storage.addPatientData(1, 75.0, "HeartRate", 1714376789050L);
        evaluateData(p1);
        assertEquals(0, alerts.size(), "Expected no abnormal heart rate alerts");


        // Test case 2: Abnormal heart rate, alerts should be triggered
        Patient p2 = new Patient(2);
        storage.addPatientData(2, 45.0, "HeartRate", 1714376789050L);
        storage.addPatientData(2, 110.0, "HeartRate", 1714376789050L);
        evaluateData(p2);
        assertEquals(2, alerts.size(), "Expected two abnormal heart rate alerts");
        assertEquals("AbnormalHeartRate", alerts.get(0).getCondition());
        assertEquals("AbnormalHeartRate", alerts.get(1).getCondition());
    }


    @Test
    void testIrregularHeartBeatAlerts() {
        alerts = new ArrayList<Alert>();
        DataStorage storage = DataStorage.getInstance();
        AlertGenerator a = new AlertGenerator(storage, alerts);

        // Test case 1: Irregular heart beat with normal heart rates
        Patient p3 = new Patient(3);
        storage.addPatientData(3, 70.0, "HeartRate", 1714376789050L); // Normal heart rate
        storage.addPatientData(3, 72.0, "HeartRate", 1714376849050L); // Irregular interval (60 seconds)
        evaluateData(p3);
        assertEquals(1, alerts.size(), "Expected one irregular heartbeat alert");
        Alert alert1 = alerts.get(0);
        assertEquals(3, alert1.getPatientId());
        assertEquals("IrregularHeartBeat", alert1.getCondition());


        // Test case 2: Multiple normal heart rates with one irregular interval
        Patient p4 = new Patient(4);
        storage.addPatientData(4, 75.0, "HeartRate", 1714376789050L); // Normal heart rate
        storage.addPatientData(4, 75.0, "HeartRate", 1714376790050L); // Regular interval (1000 milliseconds)
        storage.addPatientData(4, 75.0, "HeartRate", 1714376849050L); // Irregular interval (59000 milliseconds)
        evaluateData(p4);
        assertEquals(2, alerts.size(), "Expected one irregular heartbeat alert");
        Alert alert2 = alerts.get(1);
        assertEquals(4, alert2.getPatientId());
        assertEquals("IrregularHeartBeat", alert2.getCondition());
    }


    @Test
    void testHypotensiveHypoxemiaAlert() {
        alerts = new ArrayList<Alert>();
        DataStorage storage = DataStorage.getInstance();
        AlertGenerator a = new AlertGenerator(storage, alerts);

        // Test case 1: Hypotension and Hypoxemia Present
        Patient p5 = new Patient(5);
        storage.addPatientData(5,new double[]{85.0, 60.0}, "BloodPressure", 1714376789050L); // Hypotension
        storage.addPatientData(5, 90.0, "BloodSaturation", 1714376789050L); // Hypoxemia
        evaluateData(p5);

        assertEquals(3, alerts.size(), "Expected one CriticalBloodPressureThreshold alert, one LowBloodSaturation alert, one hypotensive hypoxemia alert");
        Alert alert1 = alerts.get(2);
        assertEquals(5, alert1.getPatientId());
        assertEquals("HypotensiveHypoxemiaAlert", alert1.getCondition());

        // Test case 2: Only Hypotension Present
        Patient p6 = new Patient(6);
        storage.addPatientData(6, new double[]{85.0, 60.0}, "BloodPressure", 1714376789050L); // Hypotension
        storage.addPatientData(6, 95.0, "BloodSaturation", 1714376789050L); // Normal saturation
        evaluateData(p6);

        assertEquals(4, alerts.size(), "Expected one CriticalBloodPressureThreshold alert");
        Alert alert2 = alerts.get(3);
        assertEquals(6, alert2.getPatientId());
        assertEquals("CriticalBloodPressureThreshold", alert2.getCondition());

        // Test case 3: Only Hypoxemia Present
        Patient p7 = new Patient(7);
        storage.addPatientData(7, new double[]{120.0, 80.0}, "BloodPressure", 1714376789050L); // Normal blood pressure
        storage.addPatientData(7, 90.0, "BloodSaturation", 1714376789050L); // Hypoxemia
        evaluateData(p7);

        assertEquals(5, alerts.size(), "Expected one LowBloodSaturation alert");
        Alert alert3 = alerts.get(4);
        assertEquals(7, alert3.getPatientId());
        assertEquals("LowBloodSaturation", alert3.getCondition());

        // Test case 4: Neither Condition Present
        Patient p8 = new Patient(8);
        storage.addPatientData(8, new double[]{120.0, 80.0}, "BloodPressure", 1714376789050L); // Normal blood pressure
        storage.addPatientData(8, 95.0, "BloodSaturation", 1714376789050L); // Normal saturation
        evaluateData(p8);

        assertEquals(5, alerts.size(), "Expected no alert");

    }


    @Test
    void testLowBloodSaturationAlerts() {
        alerts = new ArrayList<Alert>();
        DataStorage storage = DataStorage.getInstance();
        AlertGenerator a = new AlertGenerator(storage, alerts);

        // Test case 1: Low saturation
        Patient p9 = new Patient(9);
        storage.addPatientData(9, 91.0, "BloodSaturation", 1714376789050L); // Low saturation
        evaluateData(p9);

        assertEquals(1, alerts.size(), "Expected one low blood saturation alert");
        Alert alert = alerts.get(0);
        assertEquals(9, alert.getPatientId());
        assertEquals("LowBloodSaturation", alert.getCondition());

        // Test case 2: Normal saturation
        Patient p10 = new Patient(10);
        storage.addPatientData(10, 96.0, "BloodSaturation", 1714376789050L); // Normal saturation
        evaluateData(p10);

        assertEquals(1, alerts.size(), "Expected one low blood saturation alert");

    }


    @Test
    void testRapidBloodSaturationDropAlerts() {
        alerts = new ArrayList<Alert>();
        DataStorage storage = DataStorage.getInstance();
        AlertGenerator a = new AlertGenerator(storage, alerts);

        // Test case 1: Rapid Saturation Drop
        Patient p11 = new Patient(11);
        storage.addPatientData(11, 110.0, "BloodSaturation", 1714376789050L); // Normal saturation
        storage.addPatientData(11, 93.0, "BloodSaturation", 1714377389050L); // Rapid drop more than 5% within 10 minutes
        evaluateData(p11);

        assertEquals(1, alerts.size(), "Expected one rapid drop in blood saturation alert");
        Alert alert = alerts.get(0);
        assertEquals(11, alert.getPatientId());
        assertEquals("RapidBloodSaturationDrop", alert.getCondition());

        // Test case 2: No Alert
        Patient p12 = new Patient(12);
        storage.addPatientData(12, 97.0, "BloodSaturation", 1714376789050L); // Normal saturation
        storage.addPatientData(12, 95.0, "BloodSaturation", 1714377389050L); // Slight drop, but not enough for an alert

        assertEquals(1, alerts.size(), "Expected one rapid drop in blood saturation alert");
    }


    @Test
    void testBloodPressureTrendAlerts() {
        alerts = new ArrayList<Alert>();
        DataStorage storage = DataStorage.getInstance();
        AlertGenerator a = new AlertGenerator(storage, alerts);

        // Test case 1: Increasing Systolic Blood Pressure Trend
        Patient p13 = new Patient(13);
        storage.addPatientData(13, new double[]{120.0, 80.0}, "BloodPressure", 1714376789050L); // Normal
        storage.addPatientData(13, new double[]{131.0, 80.0}, "BloodPressure", 1714376889050L); // Increase > 10
        storage.addPatientData(13, new double[]{142.0, 80.0}, "BloodPressure", 1714376989050L); // Increase > 10
        storage.addPatientData(13, new double[]{153.0, 80.0}, "BloodPressure", 1714377089050L); // Increase > 10
        evaluateData(p13);

        assertEquals(1, alerts.size(), "Expected one increasing systolic blood pressure trend alert");
        Alert alert1 = alerts.get(0);
        assertEquals(13, alert1.getPatientId());
        assertEquals("IncreasingSystolicBloodPressureTrend", alert1.getCondition());

        // Test case 2: Decreasing Blood Pressure Trend
        Patient p14 = new Patient(14);
        storage.addPatientData(14, new double[]{160.0, 80.0}, "BloodPressure", 1714376789050L); // Normal
        storage.addPatientData(14, new double[]{149.0, 80.0}, "BloodPressure", 1714376889050L); // Decrease > 10
        storage.addPatientData(14, new double[]{138.0, 80.0}, "BloodPressure", 1714376989050L); // Decrease > 10
        storage.addPatientData(14, new double[]{127.0, 80.0}, "BloodPressure", 1714377089050L); // Decrease > 10
        evaluateData(p14);

        assertEquals(2, alerts.size(), "Expected one decreasing systolic blood pressure trend alert");
        Alert alert2 = alerts.get(1);
        assertEquals(14, alert2.getPatientId());
        assertEquals("DecreasingSystolicBloodPressureTrend", alert2.getCondition());

        // Test case 3: Increasing Diastolic Blood Pressure Trend
        Patient p15 = new Patient(15);
        storage.addPatientData(15, new double[]{120.0, 70.0}, "BloodPressure", 1714376789050L); // Normal
        storage.addPatientData(15, new double[]{120.0, 81.0}, "BloodPressure", 1714376889050L); // Increase > 10
        storage.addPatientData(15, new double[]{120.0, 92.0}, "BloodPressure", 1714376989050L); // Increase > 10
        storage.addPatientData(15, new double[]{120.0, 103.0}, "BloodPressure", 1714377089050L); // Increase > 10
        evaluateData(p15);

        assertEquals(3, alerts.size(), "Expected one increasing diastolic blood pressure trend alert");
        Alert alert3 = alerts.get(2);
        assertEquals(15, alert3.getPatientId());
        assertEquals("IncreasingDiastolicBloodPressureTrend", alert3.getCondition());

        // Test case 4: Decreasing Diastolic Blood Pressure Trend
        Patient p16 = new Patient(16);
        storage.addPatientData(16, new double[]{120.0, 100.0}, "BloodPressure", 1714376789050L); // Normal
        storage.addPatientData(16, new double[]{120.0, 89.0}, "BloodPressure", 1714376889050L); // Decrease > 10
        storage.addPatientData(16, new double[]{120.0, 78.0}, "BloodPressure", 1714376989050L); // Decrease > 10
        storage.addPatientData(16, new double[]{120.0, 67.0}, "BloodPressure", 1714377089050L); // Decrease > 10
        evaluateData(p16);

        assertEquals(4, alerts.size(), "Expected one decreasing diastolic blood pressure trend alert");
        Alert alert4 = alerts.get(3);
        assertEquals(16, alert4.getPatientId());
        assertEquals("DecreasingDiastolicBloodPressureTrend", alert4.getCondition());

        // Test case 5: No Alerts (Normal Blood Pressure)
        Patient p17 = new Patient(17);
        storage.addPatientData(17, new double[]{120.0, 80.0}, "BloodPressure", 1714376789050L); // Normal
        storage.addPatientData(17, new double[]{121.0, 81.0}, "BloodPressure", 1714376889050L); // Slight increase
        storage.addPatientData(17, new double[]{122.0, 82.0}, "BloodPressure", 1714376989050L); // Slight increase
        storage.addPatientData(17, new double[]{123.0, 83.0}, "BloodPressure", 1714377089050L); // Slight increase
        evaluateData(p17);

        assertEquals(4, alerts.size(), "Expected one decreasing diastolic blood pressure trend alert");

    }

    @Test
    void testCriticalBloodPressureThresholdAlert() {
        alerts = new ArrayList<Alert>();
        DataStorage storage = DataStorage.getInstance();
        AlertGenerator a = new AlertGenerator(storage, alerts);

        Patient p18 = new Patient(18);
        storage.addPatientData(18, new double[]{185.0, 80.0}, "BloodPressure", 1714376789050L); // Systolic > 180
        storage.addPatientData(18, new double[]{120.0, 130.0}, "BloodPressure", 1714376889050L); // Diastolic > 120
        storage.addPatientData(18, new double[]{85.0, 50.0}, "BloodPressure", 1714376989050L); // Systolic < 90, Diastolic < 60
        evaluateData(p18);

        assertEquals(3, alerts.size(), "Expected three critical blood pressure threshold alerts");
        assertEquals("CriticalBloodPressureThreshold", alerts.get(0).getCondition());
        assertEquals("CriticalBloodPressureThreshold", alerts.get(1).getCondition());
        assertEquals("CriticalBloodPressureThreshold", alerts.get(2).getCondition());

    }

}
