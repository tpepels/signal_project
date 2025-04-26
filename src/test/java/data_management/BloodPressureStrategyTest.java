

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import com.alerts.Alert;
import com.alerts.BloodPressureStrategy;

public class BloodPressureStrategyTest {

    @Test
    public void testHighSystolicPressure() {
        BloodPressureStrategy strategy = new BloodPressureStrategy();
        Alert alert = strategy.checkAlert("123455", "SystolicPressure", 145);
        assertNotNull(alert);
        
    }

    @Test
    public void testNormalPressure() {
        BloodPressureStrategy strategy = new BloodPressureStrategy();
        Alert alert = strategy.checkAlert("6787979", "SystolicPressure", 120);
        assertNull(alert);
    }
}
