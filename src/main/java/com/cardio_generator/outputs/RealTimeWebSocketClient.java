package com.cardio_generator.outputs;

import com.alerts.AlertGenerator;
import com.alerts.Strategy.AlertStrategy;
import com.alerts.Strategy.BloodPressureStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.Reader;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RealTimeWebSocketClient extends WebSocketClient {
    private DataStorage dataStorage;
    public Reader reader;
    public AlertGenerator alertGenerator;

    public RealTimeWebSocketClient(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
        this.reader = new Reader();
        this.alertGenerator = new AlertGenerator(dataStorage);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to WebSocket server");
    }

    @Override
    public void onMessage(String message) {

        try {
            reader.parseAndStoreDataWeb(message, dataStorage);
//            List<Patient> patientList = dataStorage.getAllPatients();
//            for (Patient patient : patientList) {
//                alertGenerator.evaluateData(patient);

            evaluateDataPeriodically();

        } catch (Exception e) {
            System.err.println("Error storing data: " + e.getMessage());
        }
    }

    private void evaluateDataPeriodically() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<Patient> patientList = dataStorage.getAllPatients();
                for (Patient patient : patientList) {
                    alertGenerator.evaluateData(patient);
                }
            }
        }, 0, 5000); // Run every 5 seconds
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from WebSocket server: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error: " + ex.getMessage());
    }
}
