package Strategies;

import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class ECGStrategy implements AlertStrategy {
    private static final double MIN_HEART_RATE = 60.0;
    private static final double MAX_HEART_RATE = 100.0;
    private static final long IRREGULAR_BEAT_TOLERANCE_MS = 200;
    private static final Logger logger = Logger.getLogger(ECGStrategy.class.getName());



    @Override
    public boolean checkAlert(PatientRecord record) {
        double heartRate = record.getMeasurementValue();
        if (heartRate < MIN_HEART_RATE || heartRate > MAX_HEART_RATE){
            logger.warning("Heart rate alert: " + heartRate);
            return true;
        }
        return false;
    }

    public boolean checkIrregularBeat(List<PatientRecord> records) {
        List<Long> timestamps = new ArrayList<>();
        List<Double> heartRates = new ArrayList<>();

        // Extract heart rate records and timestamps
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("HeartRate")) {
                heartRates.add(record.getMeasurementValue());
                timestamps.add(record.getTimestamp());
            }
        }

        for (int i = 1; i < timestamps.size(); i++) {
            long interval = timestamps.get(i) - timestamps.get(i - 1);
            if (interval != 0) {
                double expectedInterval = 60000.0 / heartRates.get(i);
                if (Math.abs(interval - expectedInterval) > IRREGULAR_BEAT_TOLERANCE_MS) {
                    logger.warning("Irregular heartbeat detected for patient " + records.get(i).getPatientId());
                    return true;
                }
            }
        }
        return false;
    }
    }

