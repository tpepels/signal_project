//package com.cardio_generator.outputs;
//
//import com.data_management.DataStorage;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//public class MainTest {
//    public static void main(String[] args) {
//        try {
//            URI serverUri = new URI("ws://localhost:8080");
//            DataStorage dataStorage = new DataStorage();
//            RealTimeWebSocketClient client = new RealTimeWebSocketClient(serverUri, dataStorage);
//            client.connect();
//
//            // Wait for some data to be received and processed
//            Thread.sleep(10000); // 10 seconds for example
//
//            // Print all data stored in DataStorage
//            dataStorage.printAllData();
//        } catch (URISyntaxException | InterruptedException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
//
