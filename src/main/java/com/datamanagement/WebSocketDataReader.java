package com.datamanagement;

import java.io.IOException;
import java.net.URI;

public class WebSocketDataReader implements DataReader {

    private final String websocketURL;

    public WebSocketDataReader(String websocketURL) {
        this.websocketURL = websocketURL;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try {
            // creates client to receive data from websocket server
            URI uri = new URI(websocketURL);
            Client client = new Client(uri, dataStorage);
            client.connectBlocking(); // pauses program until connection is established
            System.out.println("Listening for data...");
        } catch (Exception e) {
            throw new IOException("Failed to connect to WebSocket server: " + e.getMessage());
        }
    }
}
