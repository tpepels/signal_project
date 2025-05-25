package com.alerts;
import com.data_management.Patient;

public class TriggeredAlert {

    /**
     * Processes a manual trigger event.
     * 
     * @param patient    The patient for whom the alert is generated.
     * @param message    The message from the HealthDataSimulator ("ALERT: triggered" or "ALERT: resolved").
     * @param timestamp  The time the alert event occurred.
     * @return An Alert object if a relevant event occurred; otherwise, null.
     */

    public static Alert processAlert(Patient patient, String message, long timestamp){
        if (patient == null || message == null) return null;

        message = message.trim().toLowerCase();

        switch (message) {
            case "alert: triggered":
                return new Alert(String.valueOf(patient.getPatientId()),"Manual Triggered Alert", timestamp);
            case "alert: resolved":
                return new Alert(String.valueOf(patient.getPatientId()),"Manual Resolved Alert", timestamp);
            default:
                return null; 
        }
    }
}
