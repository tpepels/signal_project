package com.webSocket;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.WebSocket;
import java.net.InetSocketAddress;


public class MyWebSocketServer extends WebSocketServer {
    // Constructor: sets the port to listen on
    public MyWebSocketServer(int port) {
        super(new InetSocketAddress(port));

    }

    // Called when a client connects
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New client connected: " + conn.getRemoteSocketAddress());
        conn.send("Welcome client!");

    }

    // Called when a client disconnects
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Client disconnected: " + conn.getRemoteSocketAddress());
    }

    // Called when a message is received
    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received: " + message);
        conn.send("You said: " + message); // Echo the message
    }

    // Called when an error occurs
    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();

    }

    // Called when the server starts
    @Override
    public void onStart() {
        System.out.println("Server started");
    }

    public static void main(String[] args) {
        MyWebSocketServer server = new MyWebSocketServer(8887); // Port 8887
        server.start(); // Start the server
    }

    
}
