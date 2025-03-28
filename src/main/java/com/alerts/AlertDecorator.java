package com.alerts;

import com.data_management.Patient;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AlertDecorator implements AlertCondition{
    private final AlertCondition alertCondition;

    public AlertDecorator(AlertCondition alertCondition) {
        this.alertCondition = alertCondition;
    }

    @Override
    public List<Alert> checkCondition(Patient patient) {
        return alertCondition.checkCondition(patient);
    }
}

