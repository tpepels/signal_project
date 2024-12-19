package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

/**
 * The {@code WebSocketDataReader} class implements the {@link DataReader} interface.
 * It reads patient data from a WebSocket server and stores it in a {@link DataStorage} instance.
 */
public class WebSocketDataReader implements DataReader {

    private final WebSocketClient webSocketClient;
    private final DataStorage dataStorage;

    /**
     * Constructs a new {@code WebSocketDataReader} with the specified server URI and data storage.
     * Initializes a WebSocket client to connect to the server.
     *
     * @param serverUri    the URI of the WebSocket server
     * @param dataStorage  the data storage system to store the received data
     * @throws URISyntaxException if the server URI is not a valid URI
     */
    public WebSocketDataReader(String serverUri, DataStorage dataStorage) throws URISyntaxException {
        this.dataStorage = dataStorage;

        // Initialize WebSocketClient with the provided server URI
        this.webSocketClient = new WebSocketClient(new URI(serverUri)) {
            /**
             * Callback method invoked when the WebSocket connection is established.
             *
             * @param handshakedata the handshake data
             */
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("Connected to WebSocket server");
            }

            /**
             * Callback method invoked when a message is received from the WebSocket server.
             *
             * @param message the received message
             */
            @Override
            public void onMessage(String message) {
                processMessage(message);
            }

            /**
             * Callback method invoked when the WebSocket connection is closed.
             *
             * @param code   the status code
             * @param reason the reason for the closure
             * @param remote whether the closure was initiated by the remote host
             */
            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("Disconnected from WebSocket server");
            }

            /**
             * Callback method invoked when an error occurs in the WebSocket connection.
             *
             * @param ex the exception that occurred
             */
            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };
    }

    /**
     * Reads data from the WebSocket server by connecting the WebSocket client.
     *
     * @param dataStorage the data storage system to store the received data
     * @throws IOException if an I/O error occurs while reading data
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        this.webSocketClient.connect();
    }

    /**
     * Processes the message received from the WebSocket server and stores the data in the data storage.
     * The message format is assumed to be: patientId,timestamp,label,data.
     *
     * @param message the message received from the WebSocket server
     */
    public void processMessage(String message) {
        // Assuming the message format is: patientId,timestamp,label,data
        String[] parts = message.split(",");
        if (parts.length != 4) {
            System.err.println("Invalid message format: " + message);
            return;
        }
        try {
            int patientId = Integer.parseInt(parts[0]);
            long timestamp = Long.parseLong(parts[1]);
            String recordType = parts[2];
            double measurementValue = Double.parseDouble(parts[3]);

            // Store the data in DataStorage
            dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing message: " + message);
            e.printStackTrace();
        }
    }
}
