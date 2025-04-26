package com.alerts;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class OxygenSaturationStrategyTest {

    @Test
    public void testLowOxygenLevel() {
        OxygenSaturationStrategy strategy = new OxygenSaturationStrategy();
        Alert alert = strategy.checkAlert("9897y70", "OxygenSaturation", 85);
        assertNotNull(alert);
        
    }
}
