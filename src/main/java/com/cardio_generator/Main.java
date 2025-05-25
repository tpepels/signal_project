package com.cardio_generator;

import com.data_management.DataStorage;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length > 0 && args[0].equalsIgnoreCase("DataStorage")) {
                DataStorage.main(new String[]{});
            } else {
                HealthDataSimulator.main(new String[]{});
            }
        } catch (IOException e) {
            System.err.println("An error occurred while running the application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
