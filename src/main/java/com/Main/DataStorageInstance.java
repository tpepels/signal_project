package com.Main;

import com.data_management.CholesterolReader;
import com.data_management.DataStorage;

public class DataStorageInstance {

    private static DataStorage dataStorage;
    private static CholesterolReader reader;

    public static void setReader(CholesterolReader reader) {
        DataStorageInstance.reader = reader;
    }

    public static DataStorage getInstance() {
        if (dataStorage == null) {
            dataStorage = new DataStorage(reader);
        }
        return dataStorage;
    }
}

