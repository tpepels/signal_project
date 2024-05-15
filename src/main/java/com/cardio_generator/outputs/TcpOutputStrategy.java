package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * The TcpOutputStrategy class implements the OutputStrategy interface.
 * Listening for connection requests on a specified port.
 * Sending patient data over a TCP connection.
 *
 * @author Siyu Zhu
*/
public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

    /**
     * The TcpOutputStrategy constructor takes a port parameter and starts listening for connection requests on that port.
     * When a connection request arrives, the constructor creates a new thread to handle the connection.
     * And sends patient data to the client.
     *
     * @param port the port number where incoming connections are received.
     * @throws IOException If there's an I/O error during server socket creation or connection acceptance.
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
     * Receives patient ID, timestamp, label, and data as parameters, and formats them into a string.
     * Sends patient data over the TCP connection to the client.
     *
     * @param patientId the ID number of the patient.
     * @param timestamp the time mark of the data.
     * @param label the tag of the data.
     * @param data the real data.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
