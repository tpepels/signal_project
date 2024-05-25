package com.cardio_generator.outputs;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

import com.data_management.DataReader;
import com.data_management.DataStorage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.net.URISyntaxException;


import java.net.InetSocketAddress;

public class WSClient extends WebSocketClient {
    private DataReader dr;
    private DataStorage ds;

    public WSClient(URI serverUri, DataReader dr, DataStorage ds) {
        super(serverUri);
        this.dr = dr;
        this.ds = ds;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to server");
    }

    @Override
    public void onMessage(String s) {
        System.out.println("Storing data " + s);
        InputStream stream = new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8));
        try {
            dataProcessor.readData(dataStorage, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}

