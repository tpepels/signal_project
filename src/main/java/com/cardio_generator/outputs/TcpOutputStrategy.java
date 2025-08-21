package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Outputs patient data to a TCP client over a network connection.
 *
 * <p>Starts a TCP server on the given port and sends data to the first connected client.
 */
public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    /**
     * Creates a TCP server that listens on the specified port.
     *
     * @param port TCP port to listen on (0-65535)
     * @throws IllegalArgumentException if port is out of range
     * @throws RuntimeException if server socket cannot be started
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
     * Sends patient data as a CSV line to the connected TCP client.
     *
     * @param patientId patient identifier (must be positive)
     * @param timestamp time in milliseconds since epoch
     * @param label data type label (not null)
     * @param data data value (not null)
     * @throws IllegalArgumentException if parameters are invalid
     */

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
