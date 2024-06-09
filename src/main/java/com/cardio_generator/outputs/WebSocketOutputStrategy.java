package com.cardio_generator.outputs;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * Implements an interface that uses a WebSocket server to broadcast health data
 * The strategy initializes the WebSocket server on a specified port and sends the formatted patient data to all connected clients
 */

public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;

/**
     * Makes an output strategy that it starts a WebSocket 
     * The server broadcasts health data to all the connected WebSocket clients
     *
     * @param port number on which the WebSocket server will listen for connections
     */

    public WebSocketOutputStrategy(int port) {
        server = new SimpleWebSocketServer(new InetSocketAddress(port));
        System.out.println("WebSocket server created on port: " + port + ", listening for connections...");
        server.start();
    }

/**
 * Sends formatted patient data to all connected clients
 * The data format is "patientId,timestamp,label,data"
 * @param patientId the patient's ID 
 * @param timestamp when the data was recorded
 * @param label describes the data type 
 * @param data the measured data
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
     * Private inner class that extends WebSocketServer to manage basic WebSocket events such as opening and closing and handling server errors
     */

    private static class SimpleWebSocketServer extends WebSocketServer {

        /**
         * Makes a server to bound to the specified local socket address
         *
         * @param address address of server
         */

        public SimpleWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        /**
         * Gestiones the connection of new clients
         *
         * @param conn The WebSocket connection that has been opened.
         * @param handshake The handshake details of the connection.
         */

        @Override
        public void onOpen(WebSocket conn, org.java_websocket.handshake.ClientHandshake handshake) {
            System.out.println("New connection: " + conn.getRemoteSocketAddress());
        }

        /**
         * Handles the closure of a client connnection
         *
         * @param conn the WebSocket connection closed
         * @param code status code that says the reason closure
         * @param reason to close 
         * @param remote if the closure was initiated by remote host
         */

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
        }

        /**
         * Recieves messages from clients. it is overridden to make more functionality
         *
         * @param conn connection which the message is recieved
         * @param message received
         */
        @Override
        public void onMessage(WebSocket conn, String message) {
            
        }

        /**
         * Is going to handle errors that occur on the WebSocket connection
         *
         * @param conn connection where the errors is at
         * @param ex expection that is gonna present error
         */

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
        }

         /**
         * Log a message if the server is connected
         */

        @Override
        public void onStart() {
            System.out.println("Server started successfully");
        }
    }
}
