package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * This class implements the OutputStrategy interface to output patient information via TCP socket connection.
 * It starts a TCP server on the specified port and accepts a single client connection.
 * Patient information is sent to the connected client as a string in a comma-separated format.
 */
public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

     /**
     * Constructs a TcpOutputStrategy with the specified port number.
     * Starts a TCP server on the specified port and accepts a single client connection.
     *
     * @param port The port number on which the TCP server will listen for client connections.
     * @throws IOException if a connection between the client and the TCP server cannot be established.
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
     * Outputs the patient information to the connected client as a string in a comma-separated format.
     *
     * @param patientId patientId associated to the data.
     * @param timestamp time at which the outputted data was generated.
     * @param label label describing the type of data.
     * @param data the specific data associated with a patientId .
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
