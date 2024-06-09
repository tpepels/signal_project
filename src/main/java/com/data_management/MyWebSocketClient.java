package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.data_management.DataStorage.Data;

import java.net.URI;
import java.net.URISyntaxException;

public class MyWebSocketClient extends WebSocketClient {

    private DataStorage dataStorage;

    public MyWebSocketClient(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to WebSocket server");
    }

    @Override
    public void onMessage(String message) {
        // Parse the message and store it using DataStorage
        Data parsedData = parseMessage(message);
        dataStorage.store(parsedData);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Error occurred: " + ex.getMessage());
        // Handle errors
    }

    private Data parseMessage(String message) {
        // Parse the message and return a Data object
        return new Data(message, message, message, message);
    }

    public static void main(String[] args) throws URISyntaxException {
        DataStorage dataStorage = new DataStorage();
        MyWebSocketClient client = new MyWebSocketClient(new URI("ws://localhost:port"), dataStorage);
        client.connect();
    }
}
