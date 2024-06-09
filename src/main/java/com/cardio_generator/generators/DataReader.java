package com.cardio_generator.generators;
public interface DataReader {
    /**
     * Reads the data from an specific location and processes the data to storage it.
     *
     * @param source the data's location, it could be a directory path, URL, etc.
     */
    void readData(String source);
}
