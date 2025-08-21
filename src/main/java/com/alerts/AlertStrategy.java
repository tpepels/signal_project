package com.alerts;
import java.util.List;
import com.data_management.PatientRecord;

public interface AlertStrategy {
    boolean checkAlert(List<PatientRecord> records);
    String getCondition();
}
