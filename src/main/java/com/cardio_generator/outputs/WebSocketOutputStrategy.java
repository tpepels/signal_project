package com.cardio_generator.outputs;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * The WebSocketOutputStrategy class represents an output strategy that sends data over a WebSocket connection.
 * It implements the OutputStrategy interface.
 */
public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;

    /**
     * Constructs a WebSocketOutputStrategy object that creates a WebSocket server on the specified port.
     *
     * @param port The port on which the WebSocket server will be created.
     */
    public WebSocketOutputStrategy(int port) {
        server = new SimpleWebSocketServer(new InetSocketAddress(port));
        System.out.println("WebSocket server created on port: " + port + ", listening for connections...");
        server.start();
    }

    /**
     * Outputs the specified data for the patient over the WebSocket connection.
     *
     * @param patientId The ID of the patient.
     * @param timestamp The timestamp of the data.
     * @param label     The label or type of the data.
     * @param data      The actual data to be outputted.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
        // Broadcast the message to all connected clients
        for (WebSocket conn : server.getConnections()) {
            conn.send(message);
        }
    }

    /**
     * The SimpleWebSocketServer class represents a simple WebSocket server implementation.
     * It extends the WebSocketServer class provided by the java_websocket library.
     */
    private static class SimpleWebSocketServer extends WebSocketServer {

        /**
         * Constructs a SimpleWebSocketServer object with the specified address.
         *
         * @param address The address on which the server will listen for connections.
         */
        public SimpleWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        @Override
        public void onOpen(WebSocket conn, org.java_websocket.handshake.ClientHandshake handshake) {
            System.out.println("New connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            // Not used in this context
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
        }

        @Override
        public void onStart() {
            System.out.println("Server started successfully");
        }
    }
}
