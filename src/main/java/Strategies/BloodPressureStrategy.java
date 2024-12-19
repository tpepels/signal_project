package Strategies;

import com.data_management.PatientRecord;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Logger;

public class BloodPressureStrategy implements AlertStrategy{
    private static final double MIN_SYSTOLIC_BP = 90.0;
    private static final double MAX_SYSTOLIC_BP = 140.0;
    private static final double MIN_DIASTOLIC_BP = 60.0;
    private static final double MAX_DIASTOLIC_BP = 120.0;
    private static final double BP_TREND_THRESHOLD = 10.0;

    private static final Logger logger = Logger.getLogger(BloodPressureStrategy.class.getName());

    private static class BloodPressureMeasurement {
        double systolic;
        double diastolic;

        BloodPressureMeasurement(double systolic, double diastolic) {
            this.systolic = systolic;
            this.diastolic = diastolic;
        }
    private final Deque<BloodPressureMeasurement> measurements = new ArrayDeque<>();

    @Override
    public boolean checkAlert(PatientRecord record) {
        double[] bpValues = record.getMeasurementValues();
        double systolic = bpValues[0];
        double diastolic = bpValues[1];

        // Add current measurement to the deque
        measurements.addLast(new BloodPressureMeasurement(systolic, diastolic));

        // Keep only the last 4 measurements
        if (measurements.size() > 4) {
            measurements.removeFirst();
        }

        boolean criticalThresholdAlert = systolic < MIN_SYSTOLIC_BP || systolic > MAX_SYSTOLIC_BP ||
                diastolic < MIN_DIASTOLIC_BP || diastolic > MAX_DIASTOLIC_BP;
        boolean trendAlert = checkIncreasingSystolicTrend() || checkDecreasingSystolicTrend() ||
                checkIncreasingDiastolicTrend() || checkDecreasingDiastolicTrend();

        return criticalThresholdAlert || trendAlert;
    }

    private boolean checkIncreasingSystolicTrend() {
        if (measurements.size() < 4) return false;

        BloodPressureMeasurement[] lastFour = measurements.toArray(new BloodPressureMeasurement[0]);
        return lastFour[1].systolic > lastFour[0].systolic + BP_TREND_THRESHOLD &&
                lastFour[2].systolic > lastFour[1].systolic + BP_TREND_THRESHOLD &&
                lastFour[3].systolic > lastFour[2].systolic + BP_TREND_THRESHOLD;
    }

    private boolean checkDecreasingSystolicTrend() {
        if (measurements.size() < 4) return false;

        BloodPressureMeasurement[] lastFour = measurements.toArray(new BloodPressureMeasurement[0]);
        return lastFour[1].systolic < lastFour[0].systolic - BP_TREND_THRESHOLD &&
                lastFour[2].systolic < lastFour[1].systolic - BP_TREND_THRESHOLD &&
                lastFour[3].systolic < lastFour[2].systolic - BP_TREND_THRESHOLD;
    }

    private boolean checkIncreasingDiastolicTrend() {
        if (measurements.size() < 4) return false;

        BloodPressureMeasurement[] lastFour = measurements.toArray(new BloodPressureMeasurement[0]);
        return lastFour[1].diastolic > lastFour[0].diastolic + BP_TREND_THRESHOLD &&
                lastFour[2].diastolic > lastFour[1].diastolic + BP_TREND_THRESHOLD &&
                lastFour[3].diastolic > lastFour[2].diastolic + BP_TREND_THRESHOLD;
    }

    private boolean checkDecreasingDiastolicTrend() {
        if (measurements.size() < 4) return false;

        BloodPressureMeasurement[] lastFour = measurements.toArray(new BloodPressureMeasurement[0]);
        return lastFour[1].diastolic < lastFour[0].diastolic - BP_TREND_THRESHOLD &&
                lastFour[2].diastolic < lastFour[1].diastolic - BP_TREND_THRESHOLD &&
                lastFour[3].diastolic < lastFour[2].diastolic - BP_TREND_THRESHOLD;
    }


}
