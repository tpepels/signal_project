package com.alerts;

import java.util.List;

import com.data_management.Patient;

public class HeartRateStrategy implements AlertStrategy{

    @Override
    public List<Alert> checkAlert(Patient patient) {
        List<Alert> res = (new AbnormalDataAlert()).checkCondition(patient);

        return res;
    }
    
}
