package com.data_management;

import com.webSocket.RealTimeWebSocketClient;
import java.net.URI;

/*
 * RealTimeDataReader implements DataReader and reads real-time data
 * via WebSocket connection. It manages WebSocket client that listens to incoming data.
 */

public class RealTimeDataReader implements DataReader {

    //WebSocket client instance
    private RealTimeWebSocketClient client;


    
   /**
     * Constructor initializes the WebSocket client with the given URL.
     * 
     * @param wsUrl WebSocket server URL
     * @throws Exception if URI is invalid or client initialization fails
     */
    public RealTimeDataReader(String wsUrl) throws Exception {
        
        client = new RealTimeWebSocketClient(new URI(wsUrl));
    }

    //Establishes a blocking connection to the WebSocket server
    @Override
    public void startReading() {
        try {
            client.connectBlocking();  
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Closes the WebSocket connection
    @Override
    public void stopReading() {
        try {
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readData(DataStorage dataStorage){
        //Not used in this class
    }
}