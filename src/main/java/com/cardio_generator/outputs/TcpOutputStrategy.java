package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * A strategy for outputting patient data via TCP sockets.
 * 
 * <p>This strategy establishes a TCP server that listens for client connections on a specified port.
 * When a client connects, it sends patient data to the client over the TCP connection.
 * 
 */
public class TcpOutputStrategy implements OutputStrategy {

    /** The server socket used for accepting client connections. */
    private ServerSocket serverSocket;
    
    /** The socket for communicating with the connected client. */
    private Socket clientSocket;
    
    /** The output stream for sending data to the client. */
    private PrintWriter out;

    /**
     * Constructs a new TcpOutputStrategy that listens for client connections on the specified port.
     * 
     * @param port The port number on which the TCP server will listen for client connections.
     */
    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Outputs patient data to the connected client over the TCP connection.
     * 
     * <p>This method sends patient data to the client in the format: "patientId,timestamp,label,data".
     * 
     * @param patientId The ID of the patient associated with the data.
     * @param timestamp The timestamp indicating when the data was generated.
     * @param label The label indicating the type or category of the data.
     * @param data The actual data to be outputted.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
