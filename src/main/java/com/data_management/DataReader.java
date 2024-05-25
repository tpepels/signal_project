package com.data_management;

import java.io.IOException;
import java.io.InputStream;

public interface DataReader {
    /**
     * Reads data from a specified source and stores it in the data storage.
     *
     * @param dataStorage the storage where data will be stored
     * @return
     * @throws IOException if there is an error reading the data
     */
    DataStorage readData(DataStorage dataStorage, InputStream data) throws IOException;
}
