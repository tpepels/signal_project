package com.cardio_generator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.alerts.Alert;
import com.cardio_generator.generators.AlertGenerator;

import com.cardio_generator.generators.BloodPressureDataGenerator;
import com.cardio_generator.generators.BloodSaturationDataGenerator;
import com.cardio_generator.generators.BloodLevelsDataGenerator;
import com.cardio_generator.generators.ECGDataGenerator;
import com.cardio_generator.outputs.*;
import com.data_management.DataStorage;
import com.data_management.Patient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HealthDataSimulator {

    private static int patientCount = 50; // Default number of patients
    private static ScheduledExecutorService scheduler;
    private static OutputStrategy outputStrategy = new ConsoleOutputStrategy(); // Default output strategy
    private static com.alerts.AlertGenerator alertGenerator;
    private static DataStorage dataStorage;
    private static final Random random = new Random();
    private static RealTimeWebSocketClient webSocketClient;

    /**
     * Entry point of the HealthDataSimulator application. It parses command-line arguments, sets up the environment,
     * and schedules tasks for generating patient health data based on specified configurations.
     *
     * @param args Command-line arguments to configure the simulation.
     * @throws IOException If an I/O error occurs during setup.
     */
    public static void main(String[] args) throws IOException {

        parseArguments(args);
//        parseArguments();

        scheduler = Executors.newScheduledThreadPool(patientCount * 4);

        List<Integer> patientIds = initializePatientIds(patientCount);
        Collections.shuffle(patientIds); // Randomize the order of patient IDs

        scheduleTasksForPatients(patientIds);
        DataStorage dataStorage = new DataStorage();
        List<Patient>patients = dataStorage.getAllPatients();
        Map<Integer, Patient> patientMap = dataStorage.getPatientMap();
        for(Integer integer: patientMap.keySet()){
            System.out.println(dataStorage.getPatientMap().get(integer));
        }
        alertGenerator = new com.alerts.AlertGenerator(dataStorage);




    }
    /**
     * Parses the provided command-line arguments to set simulation parameters such as patient count and output strategy.
     * Recognizes specific flags and options to configure these parameters, and exits the application if necessary based
     * on the arguments (e.g., help option).
     *
     * @param args Command-line arguments array to be parsed.
     * @throws IOException If an I/O error occurs, particularly when setting up file-based output strategies.
     */
    private static void parseArguments(String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                    printHelp();
                    System.exit(0);
                    break;
                case "--patient-count":
                    if (i + 1 < args.length) {
                        try {
                            patientCount = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e) {
                            System.err
                                    .println("Error: Invalid number of patients. Using default value: " + patientCount);
                        }
                    }
                    break;
                case "--output":
                    if (i + 1 < args.length) {
                        String outputArg = args[++i];
                        if (outputArg.equals("console")) {
                            outputStrategy = new ConsoleOutputStrategy();
                        } else if (outputArg.startsWith("file:")) {
                            String baseDirectory = outputArg.substring(5);
                            Path outputPath = Paths.get(baseDirectory);
                            if (!Files.exists(outputPath)) {
                                Files.createDirectories(outputPath);
                            }
                            outputStrategy = new FileOutputStrategy(baseDirectory);
                        } else if (outputArg.startsWith("websocket:")) {
                            try {
                                int port = Integer.parseInt(outputArg.substring(10));
                                // Initialize your WebSocket output strategy here
                                outputStrategy = new WebSocketOutputStrategy(port);
                                System.out.println("WebSocket output will be on port: " + port);
                            } catch (NumberFormatException e) {
                                System.err.println(
                                        "Invalid port for WebSocket output. Please specify a valid port number.");
                            }
                        } else if (outputArg.startsWith("tcp:")) {
                            try {
                                int port = Integer.parseInt(outputArg.substring(4));
                                // Initialize your TCP socket output strategy here
                                outputStrategy = new TcpOutputStrategy(port);
                                System.out.println("TCP socket output will be on port: " + port);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid port for TCP output. Please specify a valid port number.");
                            }
                        } else {
                            System.err.println("Unknown output type. Using default (console).");
                        }
                    }
                    break;
                case "--websocket-client-uri":
                    if (i + 1 < args.length) {
                        initializeWebSocketClient(args[++i]);
                    } else {
                        System.err.println("Expected URI after '--websocket-client-uri'");
                        printHelp();
                        System.exit(1);
                    }
                    break;
                default:
                    System.err.println("Unknown option '" + args[i] + "'");
                    printHelp();
                    System.exit(1);
            }
        }
    }


    private static void parseArguments(){
        int port = 8080;
        // Initialize your WebSocket output strategy here
        outputStrategy = new WebSocketOutputStrategy(port);
        System.out.println("WebSocket output will be on port: " + port);
    }


    private static void initializeWebSocketClient(String serverURI) {
        try {
            URI uri = new URI(serverURI);
            webSocketClient = new RealTimeWebSocketClient(uri, dataStorage);
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            System.err.println("Invalid WebSocket server URI: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Prints help information to the console. Details usage and options available for configuring and running the
     * HealthDataSimulator.
     */
    private static void printHelp() {
        System.out.println("Usage: java HealthDataSimulator [options]");
        System.out.println("Options:");
        System.out.println("  -h                       Show help and exit.");
        System.out.println(
                "  --patient-count <count>  Specify the number of patients to simulate data for (default: 50).");
        System.out.println("  --output <type>          Define the output method. Options are:");
        System.out.println("                             'console' for console output,");
        System.out.println("                             'file:<directory>' for file output,");
        System.out.println("                             'websocket:<port>' for WebSocket output,");
        System.out.println("                             'tcp:<port>' for TCP socket output.");
        System.out.println("Example:");
        System.out.println("  java HealthDataSimulator --patient-count 100 --output websocket:8080");
        System.out.println(
                "  This command simulates data for 100 patients and sends the output to WebSocket clients connected to port 8080.");
    }

    /**
     * Initializes a list of patient IDs for use in the simulation. The IDs are sequential starting from 1 up to the
     * specified patient count.
     *
     * @param patientCount The total number of patient IDs to generate.
     * @return A list of integers representing patient IDs.
     */
    private static List<Integer> initializePatientIds(int patientCount) {
        List<Integer> patientIds = new ArrayList<>();
        for (int i = 1; i <= patientCount; i++) {
            patientIds.add(i);
        }
        return patientIds;
    }

    /**
     * Schedules simulation tasks for each patient ID in the provided list. Tasks include generating various types of health data
     * at different intervals, such as ECG, blood saturation, blood pressure, blood levels, and alert data.
     *
     * @param patientIds A list of patient IDs for which to schedule data generation tasks.
     */
    private static void scheduleTasksForPatients(List<Integer> patientIds) {
        ECGDataGenerator ecgDataGenerator = new ECGDataGenerator(patientCount);
        BloodSaturationDataGenerator bloodSaturationDataGenerator = new BloodSaturationDataGenerator(patientCount);
        BloodPressureDataGenerator bloodPressureDataGenerator = new BloodPressureDataGenerator(patientCount);
        BloodLevelsDataGenerator bloodLevelsDataGenerator = new BloodLevelsDataGenerator(patientCount);
        AlertGenerator alertGenerator = new AlertGenerator(patientCount);

        for (int patientId : patientIds) {
            scheduleTask(() -> ecgDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.SECONDS);
            scheduleTask(() -> bloodSaturationDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.SECONDS);
            scheduleTask(() -> bloodPressureDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.MINUTES);
            scheduleTask(() -> bloodLevelsDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.MINUTES);
            scheduleTask(() -> alertGenerator.generate(patientId, outputStrategy), 20, TimeUnit.SECONDS);
        }
    }

    /**
     * Schedules a single repetitive task for execution by the scheduler. The task will start after an initial random delay
     * and will execute periodically according to the specified period and time unit.
     *
     * @param task The task to be scheduled.
     * @param period The period between successive executions of the task.
     * @param timeUnit The time unit of the period and initial delay.
     */

    private static void scheduleTask(Runnable task, long period, TimeUnit timeUnit) {
        scheduler.scheduleAtFixedRate(task, random.nextInt(5), period, timeUnit);
    }

//    public void triggerManualAlert(){
//        alertGenerator.triggerAlert(new Alert(patiend))
//    }
}
