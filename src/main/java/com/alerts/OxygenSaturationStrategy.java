package com.alerts;

import java.util.List;

import com.data_management.Patient;

public class OxygenSaturationStrategy implements AlertStrategy{

    @Override
    public List<Alert> checkAlert(Patient patient) {
        List<Alert> res = (new LowSaturationAlert()).checkCondition(patient);

        return res;
    }
    
}
