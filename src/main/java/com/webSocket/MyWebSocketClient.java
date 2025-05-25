package com.webSocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;


public class MyWebSocketClient extends WebSocketClient {
    // Constructor takes a URI (e.g. ws://localhost:8887)
    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    // Called when the connection is opened
    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Connected to server");
        send("Hello from client!");
    }

    // Called when a message is received
    @Override
    public void onMessage(String message) {
        System.out.println("Server says: " + message);
    }

    // Called when connection is closed
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected");
    }

    // Called on error
    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        URI serverUri = new URI("ws://localhost:8887");
        MyWebSocketClient client = new MyWebSocketClient(serverUri);
        client.connectBlocking(); // Wait until connected
    }

    
}
