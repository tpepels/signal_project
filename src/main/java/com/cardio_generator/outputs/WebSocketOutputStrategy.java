package com.cardio_generator.outputs;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * The {@code WebSocketOutputStrategy} class implements the {@link OutputStrategy} interface.
 * It outputs patient data to connected WebSocket clients.
 */
public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;

    /**
     * Constructs a new {@code WebSocketOutputStrategy} and starts a WebSocket server on the specified port.
     *
     * @param port the port on which the WebSocket server will listen for connections
     */
    public WebSocketOutputStrategy(int port) {
        server = new SimpleWebSocketServer(new InetSocketAddress(port));
        System.out.println("WebSocket server created on port: " + port + ", listening for connections...");
        server.start();
    }

    /**
     * Outputs the specified data to all connected WebSocket clients.
     *
     * @param patientId the unique identifier of the patient
     * @param timestamp the time at which the data was generated, in milliseconds since the Unix epoch
     * @param label     the label describing the type of data
     * @param data      the data value
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
     * The {@code SimpleWebSocketServer} class is an internal class that extends {@link WebSocketServer}
     * to handle WebSocket connections, closures, and errors.
     */
    private static class SimpleWebSocketServer extends WebSocketServer {

        /**
         * Constructs a new {@code SimpleWebSocketServer} with the specified address.
         *
         * @param address the address on which the WebSocket server will listen for connections
         */
        public SimpleWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        /**
         * Called when a new WebSocket connection is opened.
         *
         * @param conn       the WebSocket connection
         * @param handshake  the handshake data
         */
        @Override
        public void onOpen(WebSocket conn, org.java_websocket.handshake.ClientHandshake handshake) {
            System.out.println("New connection: " + conn.getRemoteSocketAddress());
        }

        /**
         * Called when a WebSocket connection is closed.
         *
         * @param conn    the WebSocket connection
         * @param code    the status code
         * @param reason  the reason for the closure
         * @param remote  whether the closure was initiated by the remote peer
         */
        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
        }

        /**
         * Called when a message is received from a WebSocket connection.
         *
         * @param conn    the WebSocket connection
         * @param message the received message
         */
        @Override
        public void onMessage(WebSocket conn, String message) {
            // Not used in this context
        }

        /**
         * Called when an error occurs on a WebSocket connection.
         *
         * @param conn the WebSocket connection
         * @param ex   the exception that was thrown
         */
        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
        }

        /**
         * Called when the WebSocket server is started.
         */
        @Override
        public void onStart() {
            System.out.println("Server started successfully");
        }
    }
}
