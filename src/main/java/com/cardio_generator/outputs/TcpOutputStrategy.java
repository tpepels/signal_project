package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * {@code TcpOutputStrategy} is an implementation of the {@link OutputStrategy} interface.
 * It creates a TCP server that listens for client connections on a specified port. 
 * Once a client connects, patient data is transmitted to the client as a formatted string.
 *
 * The {@code output} method sends a message containing patient data to the connected client 
 * over the TCP connection. 
 */

public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

/**
 * Constructs a {@code TcpOutputStrategy} that listens for incoming client connections
 * on the specified TCP port. Once a client connects, data can be streamed to the client.
 *
 * This constructor starts a new thread to accept the client connection.
 *
 * @param port the port number on which the TCP server socket listens
 *
 * @throws IOException if the server socket fails to open or bind to the specified port
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

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
