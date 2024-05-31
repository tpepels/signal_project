package com.data_management;

import java.net.URI;
import java.util.Scanner;

import org.java_websocket.handshake.ServerHandshake;

public class WebSocketClient extends org.java_websocket.client.WebSocketClient{
    DataStorage storage;
    public WebSocketClient(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        storage = dataStorage;
        System.out.println("Client created successfully");
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("Client open");
        System.out.println("Connection established");
    }

    @Override
    public void onMessage(String message) {
        try {
            String[] information = message.split(",");
            if (information.length == 4) {
                if(information[3].endsWith("%")) {
                    information[3] = information[3].substring(0, information[3].length() - 1);
                }
                storage.addPatientData(Integer.parseInt(information[0]), Double.parseDouble(information[3]), information[2], Long.parseLong(information[1]));
            }
        }
        catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Closed with exit code " + code + ". Reason : " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("An error occured : " + ex);
    }
}
