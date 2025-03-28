package com.alerts;

import com.data_management.Patient;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterAlertDecorator extends AlertDecorator {
    private final Predicate<Alert> filterCondition;

    public FilterAlertDecorator(AlertCondition alertCondition, Predicate<Alert> filter) {
        super(alertCondition);
        this.filterCondition = filter;
    }

    @Override
    public List<Alert> checkCondition(Patient patient) {
        return super.checkCondition(patient).stream()
                .filter(filterCondition)
                .collect(Collectors.toList());
    }
}
