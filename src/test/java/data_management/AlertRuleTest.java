package data_management;

import com.alerts.alert_strategies.*;
import com.alerts.alerts.*;
import com.datamanagement.Patient;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlertRuleTest {

    @Test
    void testBloodPressureAlert() {

        AlertStrategy rule = new BloodPressureStrategy();
        long t = 0L;

        // 1) Systolic critical high
        Patient p1 = new Patient(1);
        p1.addRecord(200.0, "SystolicPressure", ++t);

        List<Alert> alerts = rule.checkAlert(1, p1.getRecords(t, t));
        Alert alert = new BloodPressureAlert(1, "Systolic Pressure Critically High: " + 200.0, t);
        assertEquals(alert.toString(), alerts.get(0).toString());

        // 2) Systolic critical low
        Patient p2 = new Patient(2);
        p2.addRecord(80.0, "SystolicPressure", ++t);

        alerts = rule.checkAlert(2, p2.getRecords(t, t));
        alert = new BloodPressureAlert(2, "Systolic Pressure Critically Low: " + 80.0, t);
        assertEquals(alert.toString(), alerts.get(0).toString());

        // 3) Diastolic critical high
        Patient p3 = new Patient(3);
        p3.addRecord(131.0, "DiastolicPressure", ++t);

        alerts = rule.checkAlert(3, p3.getRecords(t, t));
        alert = new BloodPressureAlert(3, "Diastolic Pressure Critically High: " + 131.0, t);
        assertEquals(alert.toString(), alerts.get(0).toString());

        // 4) Diastolic critical low
        Patient p4 = new Patient(4);
        p4.addRecord(49.0, "DiastolicPressure", ++t);

        alerts = rule.checkAlert(4, p4.getRecords(t, t));
        alert = new BloodPressureAlert(4, "Diastolic Pressure Critically Low: " + 49.0, t);
        assertEquals(alert.toString(), alerts.get(0).toString());

        // 5) Systolic trend up: 100 → 115 → 130
        Patient p5 = new Patient(5);
        p5.addRecord(100.0, "SystolicPressure", ++t);
        p5.addRecord(115.0, "SystolicPressure", ++t);
        p5.addRecord(130.0, "SystolicPressure", ++t);

        alerts = rule.checkAlert(5, p5.getRecords(t-2, t));
        alert = new BloodPressureAlert(5, "SystolicPressure trends up from: " + 100.0 + " to " + 130.0, t);
        assertEquals(alert.toString(), alerts.get(0).toString());

        // 6) Systolic trend down: 130 → 115 → 100
        Patient p6 = new Patient(6);
        p6.addRecord(130.0, "SystolicPressure", ++t);
        p6.addRecord(115.0, "SystolicPressure", ++t);
        p6.addRecord(100.0, "SystolicPressure", ++t);

        alerts = rule.checkAlert(6, p6.getRecords(t-2, t));
        alert = new BloodPressureAlert(6, "SystolicPressure trends down from: " + 130.0 + " to " + 100.0, t);
        assertEquals(alert.toString(), alerts.get(0).toString());

        // 7) Diastolic trend up: 50 → 65 → 80
        Patient p7 = new Patient(7);
        p7.addRecord(61.0, "DiastolicPressure", ++t);
        p7.addRecord(72.0, "DiastolicPressure", ++t);
        p7.addRecord(83.0, "DiastolicPressure", ++t);

        alerts = rule.checkAlert(7, p7.getRecords(t-2, t));
        alert = new BloodPressureAlert(7, "DiastolicPressure trends up from: " + 61.0 + " to " + 83.0, t);
        assertEquals(alert.toString(), alerts.get(0).toString());

        // 8) Diastolic trend down: 80 → 65 → 50
        Patient p8 = new Patient(8);
        p8.addRecord(100.0, "DiastolicPressure", ++t);
        p8.addRecord(89.0, "DiastolicPressure", ++t);
        p8.addRecord(78.0, "DiastolicPressure", ++t);

        alerts = rule.checkAlert(8, p8.getRecords(t-2, t));
        alert = new BloodPressureAlert(8, "DiastolicPressure trends down from: " + 100.0 + " to " + 78.0, t);
        assertEquals(alert.toString(), alerts.get(0).toString());
    }

    @Test
    void testBloodSaturationAlert() {

        AlertStrategy rule = new OxygenSaturationStrategy();

        // 1) Low saturation only
        Patient p1 = new Patient(1);
        p1.addRecord("95%", "Saturation", 1L);    // no alert
        p1.addRecord("91%", "Saturation", 2L);    // low → alert

        List<Alert> alerts = rule.checkAlert(1, p1.getRecords(1L, 1L));
        assertTrue(alerts.isEmpty());

        alerts = rule.checkAlert(1, p1.getRecords(2L, 2L));
        Alert alert = new BloodOxygenAlert(1, "Saturation critically low: " + 91.0, 2L);
        assertEquals(alert.toString(), alerts.get(0).toString());

        // 2) Rapid drop only
        Patient p2 = new Patient(2);
        p2.addRecord("98%", "Saturation", 10L);
        p2.addRecord("92%", "Saturation", 10L + 5 * 60 * 1000); // 5 min later, drop of 6%
        System.out.println("Rapid Drop Test: " + rule.checkAlert(p2.getPatientId(), p2.getPatientRecords()));

        alerts = rule.checkAlert(2, p2.getRecords(10L, 10L + 5 * 60 * 1000));
        alert = new BloodOxygenAlert(2, "Rapid Saturation drop from : " + 98.0 + " to " + 92.0, 10L + 5 * 60 * 1000);
        assertEquals(alert.toString(), alerts.get(0).toString());

        // 3) Both combined
        Patient p3 = new Patient(3);
        p3.addRecord("97%", "Saturation", 100L);
        p3.addRecord("90%", "Saturation", 100L + 8 * 60 * 1000); // 8 min later, drop of 7%
        System.out.println("Combined Test:   " + rule.checkAlert(p3.getPatientId(), p3.getPatientRecords()));

        alerts = rule.checkAlert(3, p3.getRecords(100L, 100L + 8 * 60 * 1000));
        alert = new BloodOxygenAlert(3, "Saturation critically low: " + 90.0, 100L + 8 * 60 * 1000);
        assertEquals(alert.toString(), alerts.get(0).toString());
        alert = new BloodOxygenAlert(3, "Rapid Saturation drop from : " + 97.0 + " to " + 90.0, 100L + 8 * 60 * 1000);
        assertEquals(alert.toString(), alerts.get(1).toString());
    }

    @Test
    void HypotensiveHypoxemiaAlert() {


        AlertStrategy rule = new HypotensiveHypoxemiaAlertStrategyStrategy();

        // Case 1: neither condition → no alerts
        Patient p1 = new Patient(1);
        p1.addRecord(120.0, "SystolicPressure", 1L);
        p1.addRecord("95%", "Saturation", 1L);

        List<Alert> alerts = rule.checkAlert(1, p1.getRecords(1,1));
        assertTrue(alerts.isEmpty());

        // Case 4: both within 10 min → one alert
        Patient p4 = new Patient(4);
        p4.addRecord("91%", "Saturation", 10_000L);
        p4.addRecord(88.0, "SystolicPressure", 15_000L);   // 5 sec later

        alerts = rule.checkAlert(4, p4.getRecords(10_000L,15_000L));
        Alert alert = new HypotensiveHypoxemiaAlert(4, "HypotensiveHypoxemia", 15_000L);
        assertEquals(alert.toString(), alerts.get(0).toString());

        // Case 5: both but >10 min apart → no alert
        Patient p5 = new Patient(5);
        p5.addRecord("90%", "Saturation", 20_000L);
        p5.addRecord(85.0, "SystolicPressure", 15_000L - 700_000L); // >10 min before

        alerts = rule.checkAlert(5, p5.getRecords(20_000L,700_000L));
        assertTrue(alerts.isEmpty());

        // Case 6: multiple matching pairs → one alert per low‐sat reading
        Patient p6 = new Patient(6);
        p6.addRecord("89%", "Saturation", 30_000L);
        p6.addRecord(92.0, "SystolicPressure", 31_000L);
        p6.addRecord(87.0, "SystolicPressure", 35_000L);   // second low‐BP

        alerts = rule.checkAlert(6, p6.getRecords(30_000L,35_000L));
        alert = new HypotensiveHypoxemiaAlert(6, "HypotensiveHypoxemia", 35_000L);
        assertEquals(alert.toString(), alerts.get(0).toString());
    }

    @Test
    void ECGAlert() {

        AlertStrategy rule = new ECGStrategy();
        String[] differentTypes = {"Cholesterol", "DiastolicPressure", "RedBloodCells", "Saturation", "SystolicPressure", "WhiteBloodCells"};
 
        // 1) Test each type for a large baseline then a >25% spike (should alert)
        for (int idx = 0; idx < differentTypes.length; idx++) {
            String type = differentTypes[idx];
            Patient pInc = new Patient(100 + idx);
 
            // 10 steady readings at 100.0 every 5s
            for (int i = 0; i < 10; i++) {
                pInc.addRecord(100.0, type, i * 5_000L);
            }
            // 11th reading jumps to 140.0 (40% ↑) at t=50s
            pInc.addRecord(140.0, type, 10 * 5_000L);
            
            Alert alert = new ECGAlert(100 + idx, "Unexpected Value Increase for " + differentTypes[idx] + " to " + 140.0 + " from the average of: " + 100.0,50_000L);
            List<Alert> alerts = rule.checkAlert(100 + idx, pInc.getRecords(0L,50_000L));
            assertEquals(alert.toString(), alerts.get(0).toString());
        }
 
        // 2) Test each type for a large baseline then a >25% drop (should alert)
        for (int idx = 0; idx < differentTypes.length; idx++) {
            String type = differentTypes[idx];
            Patient pDec = new Patient(200 + idx);
    
            // 10 steady readings at 100.0 every 5s
            for (int i = 0; i < 10; i++) {
                pDec.addRecord(100.0, type, i * 5_000L);
            }
            // 11th reading drops to  60.0 (40% ↓) at t=50s
            pDec.addRecord( 60.0, type, 10 * 5_000L);

            Alert alert = new ECGAlert(200 + idx, "Unexpected Value Decrease for " + differentTypes[idx] + " to " + 60.0 + " from the average of: " + 100.0,50_000L);
            List<Alert> alerts = rule.checkAlert(200 + idx, pDec.getRecords(0L,50_000L));
            assertEquals(alert.toString(), alerts.get(0).toString());
        }
 
        // 3) Mixed‐type stream: only the spiking/dropping labels should fire
        Patient pmix = new Patient(999);
        long t = 0;
        // 5 baseline values for every type
        for (String type : differentTypes) {
            for (int i = 0; i < 5; i++) {
                pmix.addRecord(50.0, type, t);
                t += 1_000L;
            }
        }
        // add one 80.0 spike for the first type
        pmix.addRecord(80.0, differentTypes[0], t); t += 1_000L;
        // add one 10.0 drop for the second type
        pmix.addRecord(10.0, differentTypes[1], t);

        List<Alert> alerts = rule.checkAlert(999, pmix.getRecords(0L,31_000L));

        Alert alert = new ECGAlert(999, "Unexpected Value Increase for " + differentTypes[0] + " to " + 80.0 + " from the average of: " + 50.0,30_000L);
        assertEquals(alert.toString(), alerts.get(0).toString());

        alert = new ECGAlert(999, "Unexpected Value Decrease for " + differentTypes[1] + " to " + 10.0 + " from the average of: " + 50.0,31_000L);
        assertEquals(alert.toString(), alerts.get(1).toString());

        // 4) Out‐of‐window eviction test: spike but not enough records remain → no alerts
        Patient pout = new Patient(888);
        // 5 readings at t=0,10s,20s,30s,40s
        for (int i = 0; i < 5; i++) {
            pout.addRecord(100.0, differentTypes[2], i * 10_000L);
        }
        // at t=200s all 5 have aged out of the 60s window → window size<5
        pout.addRecord(200.0, differentTypes[2], 200_000L);
        
        alerts = rule.checkAlert(888, pout.getRecords(0L,200_000L));
        assertTrue(alerts.isEmpty());
    }



}



