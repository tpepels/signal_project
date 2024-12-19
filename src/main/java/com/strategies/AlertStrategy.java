package com.strategies;

import com.data_management.PatientRecord;

/**
 * The {@code AlertStrategy} interface defines a strategy for checking alert conditions
 * on patient records. Implementations of this interface will provide specific criteria
 * for triggering alerts based on the data contained in {@link PatientRecord} objects.
 */
public interface AlertStrategy {

    /**
     * Checks whether the given patient record meets the criteria for triggering an alert.
     *
     * @param record the patient record to check
     * @return {@code true} if the record meets the alert criteria, {@code false} otherwise
     */
    boolean checkAlert(PatientRecord record);
}

