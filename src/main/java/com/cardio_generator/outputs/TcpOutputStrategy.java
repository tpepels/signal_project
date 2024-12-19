package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
/**
 * Implements an OutputStrategy to send data over a TCP connection.
 * This class sets up a TCP server that waits for a client connection and then sends data
 * over this connection formatted as a CSV string whenever output is called.
 *
 * Usage: Instantiate this with a specific TCP port and use the output method to send data
 * to a connected TCP client.
 *
 * @author Tepels
 */

public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

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
     * Sends data to a connected TCP client formatted as CSV. The data includes the patient ID,
     * timestamp, label, and the data string.
     *
     * @param patientId the unique identifier of the patient
     * @param timestamp the timestamp at which the data is applicable
     * @param label the label describing the type of data
     * @param data the actual data string to be sent
     */

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
