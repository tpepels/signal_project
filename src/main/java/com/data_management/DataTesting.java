package main.java.com.data_management;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

public class AlertGeneratorTest {
    private AlertGenerator alertGenerator;


    public void setUp() {
        alertGenerator = new AlertGenerator(new DataStorage(), 5);
    }

    
    public void testCheckTrendAlertWithIncreasingTrend() {
        List<Double> increasingTrendReadings = Arrays.asList(100.0, 110.0, 120.0, 130.0, 140.0);
        assertTrue("Trend alert should be triggered for increasing trend",
                   alertGenerator.checkTrendAlert(increasingTrendReadings));
    }

    public void testCheckTrendAlertWithDecreasingTrend() {
        List<Double> decreasingTrendReadings = Arrays.asList(140.0, 130.0, 120.0, 110.0, 100.0);
        assertTrue("Trend alert should be triggered for decreasing trend",
                   alertGenerator.checkTrendAlert(decreasingTrendReadings));
    }

    public void testCheckTrendAlertWithNoSignificantChange() {
        List<Double> stableReadings = Arrays.asList(100.0, 101.0, 102.0, 103.0, 104.0);
        assertFalse("Trend alert should not be triggered for stable readings",
                    alertGenerator.checkTrendAlert(stableReadings));
    }

}
