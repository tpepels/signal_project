import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.alerts.Alert;
import com.alerts.HeartRateStrategy;

public class HeartRateStrategyTest {

    @Test
    public void testHighHeartRate() {
        HeartRateStrategy strategy = new HeartRateStrategy();
        Alert alert = strategy.checkAlert("knknknlkn", "HeartRate", 130);
        assertNotNull(alert);
        
    }
}
