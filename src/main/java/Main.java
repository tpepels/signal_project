import com.cardio_generator.HealthDataSimulator;
import com.data_management.DataStorage;

import java.io.IOException;

/**
 * The {@code Main} class serves as the entry point for the application.
 * It determines whether to run the data storage or the health data simulator based on the provided command-line arguments.
 */
public class Main {

    /**
     * The main method that serves as the entry point for the application.
     * It checks the command-line arguments to decide whether to run the data storage or the health data simulator.
     *
     * @param args the command-line arguments
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equals("DataStorage")) {
            DataStorage.getInstance().runDataStorage(); // Assuming a runDataStorage method is needed
        } else {
            HealthDataSimulator.getInstance().runSimulation(args);
        }
    }
}

