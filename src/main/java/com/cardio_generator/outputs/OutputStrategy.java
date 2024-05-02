package com.cardio_generator.outputs;

/**
 * This is going to Specify the requirements for the outputs methods in the simulation destinations can be like TCP, WebSocket, console...
*/

public interface OutputStrategy {

    /**
     * Formatted patient data
     * This method is going to be implemented so it defines how the data is send out
     *
     * @param patientId  identifier for the patient whose data is displayed
     * @param timestamp when the data was obtained
     * @param label for the type of data
     * @param data to be output, string
     */

    void output(int patientId, long timestamp, String label, String data);
}
