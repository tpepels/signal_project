package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * The {@code WebSocketClientImpl} class is an implementation of the {@link WebSocketClient}.
 * It connects to a WebSocket server, receives messages, processes them, and stores the processed data in {@link DataStorage}.
 */
public class WebSocketClientImpl extends WebSocketClient {
    private final DataStorage dataStorage;

    /**
     * Constructs a new {@code WebSocketClientImpl}.
     *
     * @param serverUri   The URI of the WebSocket server to connect to.
     * @param dataStorage The DataStorage instance to store the processed data.
     * @throws URISyntaxException If the serverUri is not a valid URI.
     */
    public WebSocketClientImpl(String serverUri, DataStorage dataStorage) throws URISyntaxException {
        super(new URI(serverUri));
        this.dataStorage = dataStorage;
    }

    /**
     * Callback method invoked when the WebSocket connection is established.
     *
     * @param serverHandshake the handshake data
     */
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to WebSocket server");
    }

    /**
     * Callback method invoked when a message is received from the WebSocket server.
     *
     * @param message the received message
     */
    @Override
    public void onMessage(String message) {
        try {
            processMessage(message);
        } catch (Exception e) {
            System.err.println("Error processing message: " + message);
            e.printStackTrace();
        }
    }

    /**
     * Callback method invoked when the WebSocket connection is closed.
     *
     * @param code    the status code
     * @param reason  the reason for the closure
     * @param remote  whether the closure was initiated by the remote host
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from WebSocket server: " + reason);
    }

    /**
     * Callback method invoked when an error occurs in the WebSocket connection.
     *
     * @param e the exception that occurred
     */
    @Override
    public void onError(Exception e) {
        System.err.println("WebSocket error occurred: " + e.getMessage());
        e.printStackTrace();
    }

    /**
     * Processes the message received from the WebSocket server and stores the data in the data storage.
     * The message format is assumed to be: patientId,timestamp,label,data.
     *
     * @param message the message received from the WebSocket server
     */
    private void processMessage(String message) {
        String[] parts = message.split(",");
        if (parts.length != 4) {
            System.err.println("Invalid message format: " + message);
            return;
        }

        int patientId;
        try {
            patientId = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid patient ID: " + parts[0]);
            return;
        }

        long timestamp;
        try {
            timestamp = Long.parseLong(parts[1]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid timestamp: " + parts[1]);
            return;
        }

        String recordType = parts[2];
        double measurementValue;
        try {
            measurementValue = Double.parseDouble(parts[3]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid measurement value: " + parts[3]);
            return;
        }

        // Store the data in DataStorage
        dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
    }
}
