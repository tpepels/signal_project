package com.cardio_generator.outputs;

import com.data_management.DataStorage;
import com.data_management.Reader;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class RealTimeWebSocketClient extends WebSocketClient {
    private DataStorage dataStorage;
    private Reader reader;

    public RealTimeWebSocketClient(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
        this.reader = new Reader();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to WebSocket server");
    }

    @Override
    public void onMessage(String message) {
        // Parse message and store it using DataStorage
        try {
            reader.parseAndStoreData(message, dataStorage);
        } catch (Exception e) {
            System.err.println("Error storing data: " + e.getMessage());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from WebSocket server: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error: " + ex.getMessage());
    }

}
