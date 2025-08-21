package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MyWebSocketClient extends WebSocketClient {
    private final DataStorage dataStorage;

    // Constructor takes URI and DataStorage
    public MyWebSocketClient(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to server");
    }

    // Called when a message is received
    @Override
    public void onMessage(String message) {
        System.out.println("Server says: " + message);
        try {
            String[] parts = message.split("\\|");
            if (parts.length != 4) {
                System.err.println("Invalid message format: " + message);
                return;
            }

            int patientId = Integer.parseInt(parts[0]);
            double value = Double.parseDouble(parts[1]);
            String type = parts[2];
            long timestamp = Long.parseLong(parts[3]);

            dataStorage.addPatientData(patientId, value, type, timestamp);

        } catch (Exception e) {
            System.err.println("Failed to process message: " + message);
            e.printStackTrace();
        }
    }

    // Called when connection is closed
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from server. Reason: " + reason);
    }

    // Called on error
    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error occurred:");
        ex.printStackTrace();
    }
}
