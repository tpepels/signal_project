package com.alerts;

import java.util.HashMap;
import java.util.List;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.TrendWindow;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    private final double systolicMin = 90.0;
    private final double systolicMax = 180.0;
    private final double diastolicMin = 60.0;
    private final double diastolicMax = 120.0;
    private final double heartRateMin = 50.0;
    private final double heartRateMax = 100;
    private final double saturationMin = 92.0;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public double getSystolicMin() {
        return systolicMin;
    }

    public double getSystolicMax() {
        return systolicMax;
    }

    public double getDiastolicMin() {
        return diastolicMin;
    }

    public double getDiastolicMax() {
        return diastolicMax;
    }

    public double getHeartRateMin() {
        return heartRateMin;
    }

    public double getHeartRateMax() {
        return heartRateMax;
    }

    public double getSaturationMin() {
        return saturationMin;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert} method. This method should define the specific conditions under which an
     * alert will be triggered.
     *
     * @param patient          the patient data to evaluate for alert conditions
     * @param trendWindowSize  the size of the trend window
     */
    public void evaluateData(Patient patient) {
        com.data_management.TrendWindow trendWindow = new TrendWindow(patient);
    long startTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000); // 24 hours ago
    long endTime = System.currentTimeMillis(); // Current time

    List<Double> systolicReadings = trendWindow.getWindowValues(patient.getPatientId(), startTime, endTime, "Systolic blood pressure"); 
    List<Double> diastolicReadings = trendWindow.getWindowValues(patient.getPatientId(), startTime, endTime, "Diastolic blood pressure"); 
    List<Double> saturationReadings = trendWindow.getWindowValues(patient.getPatientId(), startTime, endTime, "Blood saturation"); 
    List<Double> heartRateReadings = trendWindow.getWindowValues(patient.getPatientId(), startTime, endTime, "Heart rate"); 

   
}

    private boolean checkRapidDropAlert(List<Double> saturationReadings) {
        int dropCount = 0;
        double previousReading = 0.0;

        for (double reading : saturationReadings) {
            if (previousReading != 0.0 && (previousReading - reading) >= 5.0) {
                dropCount++;
            }
            previousReading = reading;
        }

        return dropCount >= 3; // Trigger alert if there are 3 or more rapid drops in saturation
    }

    private boolean checkIrregularBeatAlert(List<Double> heartRateReadings) {
        for (int i = 1; i < heartRateReadings.size(); i++) {
            double current = heartRateReadings.get(i);
            double previous = heartRateReadings.get(i - 1);

            if (Math.abs(current - previous) >= 30) {
                return true; // Trigger alert if heart rate varies by 30 or more bpm
            }
        }

        return false;
    }

    private boolean checkHypotensiveHypoxemiaAlert(List<Double> systolicReadings, List<Double> saturationReadings) {
        // Check if systolic blood pressure is below 90 and saturation is below 92%
        boolean systolicCritical = false; 
        boolean saturationCritical = false; 

        for(int i = 0; i<systolicReadings.size(); i++){
            if(systolicReadings.get(i) < systolicMin){
                systolicCritical = true; 
            }
        }
        for(int i = 0; i<saturationReadings.size(); i++){
            if(saturationReadings.get(i) < saturationMin){
                saturationCritical = true; 
            }
        }
        if(systolicCritical == true && saturationCritical == true){
            return true; 
        }
        return false; 
    }

    /**
     * Checks if the critical threshold condition is met for a given set of readings.
     *
     * @param chart the list of readings to check
     * @param lower the lower threshold
     * @param upper the upper threshold
     * @return true if the critical threshold condition is met, false otherwise
     */
    private boolean criticalThreshold(List<Double> chart, final double lower, final double upper) {
        for (double value : chart) {
            if (value < lower || value > upper) {
                return true;
            }
        }
        return false;
    }

    /**
     * Triggers an alert for the monitoring system.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        System.out.println(alert);
        notifyPersonnel(alert);
        displayAlertOnDashboard(alert);
        // Implementation might involve logging the alert or notifying staff
    }

    private void notifyPersonnel(Alert alert) {
        if (alert.getCondition().equals("Critical Threshold")) {
            System.out.println("Paging medical team of " + alert.getPatientId() + "Patient is " + alert.getCondition());
        }
        else if(alert.getCondition().equals("Concerning trend in chart")){
            System.out.println("Paging available nurses of staff " + alert.getPatientId() + "Patient chart is looking concerning " + "Patient is" + alert.getCondition()); 
        }
    }

    private void displayAlertOnDashboard(Alert alert) {
        System.out.println("Alert displayed on patient dashboard: Patient ID - " + alert.getPatientId() +
                ", Condition - " + alert.getCondition() +
                ", Timestamp - " + alert.getTimestamp());
    }
}
