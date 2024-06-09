package com.cardio_generator.outputs;

import com.cardio_generator.HealthDataSimulator;
import com.data_management.DataStorage;
import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Collection;

public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;
    private RealTimeWebSocketClient client;
    private DataStorage dataStorage;

    // Constructor for dependency injection
    public WebSocketOutputStrategy(WebSocketServer server, RealTimeWebSocketClient client, DataStorage dataStorage) {
        this.server = server;
        this.client = client;
        this.dataStorage = dataStorage;
        this.server.start();
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

    // This allows to set a new client at runtime
    public void setClient(RealTimeWebSocketClient client){
        this.client = client;
    }
}
