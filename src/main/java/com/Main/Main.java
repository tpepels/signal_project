package com.Main;

import com.cardio_generator.HealthDataSimulator;
import com.data_management.CholesterolReader;
import com.data_management.DataStorage;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            switch (args[0]) {
                case "DataStorage":
                    DataStorage.main(new String[]{});
                    break;
                case "CholesterolReader":
                    CholesterolReader.main(new String[]{});
                    break;
                case "HealthDataSimulator":
                    String[] arguments = new String[args.length - 1];
                    for (int i = 0; i < arguments.length; i++) {
                        arguments[i] = args[i+1];
                    }
                    HealthDataSimulator.main(arguments);
                    break;
                default:
                    System.out.println("Invalid argument. Please provide one of the following arguments: DataStorage, CholesterolReader, HealthDataSimulator");
                    break;
            }
        }
    }
}
