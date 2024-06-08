package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * the TcpOutputStrategy class implements the OutputStrategy interface to provide an output strategy that sends data over TCP
 */
public class TcpOutputStrategy implements OutputStrategy {//Class names are written in UpperCamelCase.

    /** the server socket used to accept incoming connections. */
    private ServerSocket serverSocket;

    /** the client socket used to communicate with the connected client. */
    private Socket clientSocket;

    /** the output stream writer used to send data to the client. */
    private PrintWriter out;

    /**
     * constructs a new TcpOutputStrategy with the specified port number.
     *
     * @param port the port number on which the TCP server will listen to recieve data
     */
    public TcpOutputStrategy(int port) {
        /**
         * @throws exception in the case that a connection with the client socket cannot be build
         */
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                /**
                 * @throws exception in the case that a data cannot be recieved from the client
                 */
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
     * outputs the generated data for a specific patient
     *
     * @param patientId the id of the patient
     * @param timestamp the timestamp contains the time at which it was generated
     * @param label the label associated with the data
     * @param data the data to be output
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
