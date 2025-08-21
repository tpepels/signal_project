package com.alerts.alert_strategies;

import com.datamanagement.PatientRecord;

import java.util.List;
import java.util.stream.Collectors;

public class AlertStrategyUtils {


    public AlertStrategyUtils() {

    }

    protected static Double parseDouble(String str) {
        try {
            // strip out any non-digit/decimal (e.g. “%”) first if you like:
            return Double.parseDouble(str.replaceAll("[^0-9.–]", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    public List<PatientRecord> getRecords(List<PatientRecord> records, String type) {
        return records.stream().
                filter(record -> record.getRecordType().equals(type)).
                collect(Collectors.toList());
    }
    public List<PatientRecord> getRecords(List<PatientRecord> records, String type, String type2) {
        return records.stream().
                filter(record -> (record.getRecordType().equals(type)||
                        record.getRecordType().equals(type2))).
                collect(Collectors.toList());
    }
}
