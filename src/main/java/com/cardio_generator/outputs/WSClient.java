package com.cardio_generator.outputs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import com.data_management.DataReader;
import com.data_management.DataStorage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

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
    public void onMessage(String message) {
        System.out.println("Storing data " + message);
        InputStream stream = new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8));
        try {
            dr.readData(ds, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("An error occurred:" + ex);
    }
}

