package com.cardio_generator.outputs;

/**
 * 
 * this is going to Specify the requirements for the outputs methods in the simulation 
 * destinations can be like TCP, WebSocket, console...
*/

public interface OutputStrategy {

    /**
     * formatted patient data
     *this method is going to be implemented so it defines how the data is send out
     *
     * @param patientId  identifier for the patient whose data is displayed
     * @param timestamp The timestamp when the data was obtained
     * @param label  label for the type of data
     * @param data data to be output, string
     */

    void output(int patientId, long timestamp, String label, String data);
}
