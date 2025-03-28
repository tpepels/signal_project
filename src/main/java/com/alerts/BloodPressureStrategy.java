package com.alerts;

import java.util.ArrayList;
import java.util.List;

import com.data_management.Patient;

public class BloodPressureStrategy implements AlertStrategy{

    @Override
    public List<Alert> checkAlert(Patient patient) {
        List<Alert> res = new ArrayList<>();

        List<Alert> thresholdAlerts = (new CriticalThresholdAlert()).checkCondition(patient);
        List<Alert> trendAlerts = (new TrendAlert()).checkCondition(patient);

        for (Alert i : thresholdAlerts) {
            res.add(i);
        }
        for (Alert i : trendAlerts) {
            res.add(i);
        }

        return res;
    }
    
}
