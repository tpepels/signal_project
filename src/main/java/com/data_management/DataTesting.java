package com.data_management;

import com.alerts.AlertGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataTesting {
    private AlertGenerator alertGenerator;

    @BeforeEach
    public void setUp() {
        DataStorage dataStorage = new DataStorage(); // Assuming you have a DataStorage class
        alertGenerator = new AlertGenerator(dataStorage, 5); // Assuming the constructor expects both DataStorage and int
    }

    @Test
    public void testCheckTrendAlertWithIncreasingTrend() {
        List<Double> increasingTrendReadings = Arrays.asList(100.0, 110.0, 120.0, 130.0, 140.0);
        assertTrue("Trend alert should be triggered for increasing trend",
                alertGenerator.checkTrendAlert(increasingTrendReadings)); // Assuming checkTrendAlert is defined in AlertGenerator
    }

    @Test
    public void testCheckTrendAlertWithDecreasingTrend() {
        List<Double> decreasingTrendReadings = Arrays.asList(140.0, 130.0, 120.0, 110.0, 100.0);
        assertTrue("Trend alert should be triggered for decreasing trend",
                alertGenerator.checkTrendAlert(decreasingTrendReadings)); // Assuming checkTrendAlert is defined in AlertGenerator
    }

    @Test
    public void testCheckTrendAlertWithNoSignificantChange() {
        List<Double> stableReadings = Arrays.asList(100.0, 101.0, 102.0, 103.0, 104.0);
        assertFalse("Trend alert should not be triggered for stable readings",
                alertGenerator.checkTrendAlert(stableReadings)); // Assuming checkTrendAlert is defined in AlertGenerator
    }
}