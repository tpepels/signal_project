package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * implements the interface to send data to clients over TCP
 * it sets a TCP server on a port and sends formatted patient data
 * to a connected client
 */

public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

    /**
     * mqke a TCP and it will start a TCP server on port
     * server will wait for a client to be connected and if connection if sucssesful is sending data
     *
     * @param port port number which the TCP server will listen for client connections
     */

    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // get client in a new thread to not make problems in the other thread
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
     * sends formatteed patient data to client over TCP
     * data is formatted and sent as a single line
     *
     * @param patientId  ID of the patient that it's data is being sent
     * @param timestamp timestamp of the data measurement
     * @param label describes the type of the data
     * @param data data measured for patient
     */

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
