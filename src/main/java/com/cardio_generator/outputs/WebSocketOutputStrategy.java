package com.cardio_generator.outputs;

import com.cardio_generator.HealthDataSimulator;
import com.data_management.DataStorage;
import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Set;

public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;
    private RealTimeWebSocketClient client;
    public DataStorage dataStorage;

    public WebSocketOutputStrategy(int port) {
        HealthDataSimulator simulator = HealthDataSimulator.getInstance();
        this.dataStorage = DataStorage.getDataStorageInstance();

        server = new SimpleWebSocketServer(new InetSocketAddress(port));
        System.out.println("WebSocket server created on port: " + port + ", listening for connections...");

        server.start();
        simulator.initializeWebSocketClient("ws://127.0.0.1:8080/");
        this.client = simulator.getClient();
    }

    public void addClient(RealTimeWebSocketClient client){
        this.client = client;
    }
    public WebSocketOutputStrategy(RealTimeWebSocketClient client) {
        this.client = client;
    }

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
       broadcastMessage(message);
    }


    private void broadcastMessage(String message) {
        Collection<WebSocket> connections = server.getConnections();
        synchronized (connections) {
            for (WebSocket conn : connections) {
                conn.send(message);
            }
        }
    }

    private static class SimpleWebSocketServer extends WebSocketServer {

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
