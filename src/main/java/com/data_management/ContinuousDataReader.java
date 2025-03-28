package com.data_management;

import java.io.IOException;
import java.net.URI;

public class ContinuousDataReader implements DataReader{
    private WebSocketClient client;
    private URI serverUri;

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        client = new WebSocketClient(serverUri, dataStorage);
    }

    public void setServerUri(URI uri) {
        serverUri = uri;
    }
}
