package com.datamanagement;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Client extends WebSocketClient {

    private final DataStorage dataStorage;

    public Client(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
    }

    @Override
	public void onOpen(ServerHandshake handshakedata) {
		System.out.println("new connection opened");
	}

    @Override
	public void onClose(int code, String reason, boolean remote) {
		System.out.println("closed with exit code " + code + " additional info: " + reason);
	}

    @Override
	public void onMessage(String message) {
		System.out.println("received message: " + message);

        // Message format: "patientId,timestamp,label,data"
        try {
            // read patient record data from message string and store it in dataStorage
            String[] parts = message.split(",");
            
            int patientId = Integer.parseInt(parts[0]);
            long timestamp = Long.parseLong(parts[1]);
            String label = parts[2];
            String value = parts[3];

            dataStorage.addPatientData(patientId, value, label, timestamp);
            
        } catch (Exception e) {
            System.err.println("an error occurred while parsing the message: " + message + "\n" + e.getMessage());
        }
	}

    @Override
	public void onError(Exception e) {
		System.err.println("an error occurred:" + e);
	}
}