package alert;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.alerts.Alert;
import com.alerts.AlertFactory;
import com.alerts.BloodOxygenAlert;
import com.alerts.BloodOxygenAlertFactory;
import com.alerts.BloodPressureAlert;
import com.alerts.BloodPressureAlertFactory;
import com.alerts.ECGAlert;
import com.alerts.ECGAlertFactory;

public class AlertFactoryTest {
    @Test
    public void testAlertFactory() {
        AlertFactory factory = new AlertFactory();
        Alert alert = factory.createAlert("1", "Test", 1000);

        assertEquals("Alert type is not correctly initiated", mock(Alert.class).getClass(), alert.getClass());
    }
    
    @Test
    public void testBloodPressureAlertFactory() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("1", "Test", 1000);

        assertEquals("Blood Pressure Alert type is not correctly initiated", mock(BloodPressureAlert.class).getClass(), alert.getClass());
    }
    
    @Test
    public void testBloodOxygenAlertFactory() {
        AlertFactory factory = new BloodOxygenAlertFactory();
        Alert alert = factory.createAlert("1", "Test", 1000);

        assertEquals("Blood Oxygen Alert type is not correctly initiated", mock(BloodOxygenAlert.class).getClass(), alert.getClass());
    }
    
    @Test
    public void testECGAlertFactory() {
        AlertFactory factory = new ECGAlertFactory();
        Alert alert = factory.createAlert("1", "Test", 1000);

        assertEquals("ECG Alert type is not correctly initiated", mock(ECGAlert.class).getClass(), alert.getClass());
    }
}
