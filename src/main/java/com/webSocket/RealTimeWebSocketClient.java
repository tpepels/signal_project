package com.webSocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;

import com.data_management.DataStorageSingleton;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * RealTimeWebSocketClient extends WebSocketClient to handle incoming real-time
 * patient data over WebSocket connection. It parses incoming JSON messages
 * and stores the data in the DataStorageSingleton
 * 
 * Expected JSON message:
 * {
  "patientId": 1,
  "measurementValue": 120,
  "recordType": "SystolicPressure",
  "timestamp": 1716123910000
    }
 */

public class RealTimeWebSocketClient extends WebSocketClient {

    //Object mapper to parsing JSON messages
    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * Constructor sets WebSocket server URI
     * @param serverUri URI fo the WebScoket server
     *
     */
    public RealTimeWebSocketClient(URI serverUri){
        super(serverUri);
    }

    //Called when connection is established
    @Override
    public void onOpen(ServerHandshake handshake){
        System.out.println("Connected to WebSocket server.");
    }


    /**
     * Handles incoming messages from the WebSocket server,
     * parses the message and stores the data
     * @param message JSON-formated patient data string
     */
    @Override
    public void onMessage(String message){
        System.out.println("Received: "+ message);
        
        try{
            //Parse incoming JSON stirng
            JsonNode jsonNode = objectMapper.readTree(message);

            //Validate required fields
            if (!jsonNode.hasNonNull("patientId") || !jsonNode.hasNonNull("measurementValue") || !jsonNode.hasNonNull("recordType") || !jsonNode.hasNonNull("timestamp")) {
                throw new IllegalArgumentException("Missing required fields in JSON message.");
            }
            
            //Extract patient data fields
            int patientId = jsonNode.get("patientId").asInt();
            double measurementValue = jsonNode.get("measurementValue").asDouble();
            String recordType = jsonNode.get("recordType").asText();
            long timestamp = jsonNode.get("timestamp").asLong();

            //Store the data
            DataStorageSingleton.getInstance().addPatientData(patientId, measurementValue, recordType, timestamp);
            System.out.println("Stored record for patient ID: "+ patientId);

        } catch(IllegalArgumentException e){
            System.out.println("Validation error: " + e.getMessage());
        } catch(Exception e){
            System.out.println("Error parsing JSON message: " + message);
            e.printStackTrace();
        }
    }


    /**
     * Called when WebSocket connection is closed
     * @param code Closure code
     * @param reason Reason for closure
     * @param remote True if closed by server
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from WebSocket server. Reason: "+reason);
    }

    //Handles errors that occur during WebSocket communication
    @Override
    public void onError(Exception ex){
        System.out.println("WebSocket error:");
        ex.printStackTrace();
    }
}
