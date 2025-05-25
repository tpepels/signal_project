package com.data_management;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cardio_generator.generators.*;
import com.cardio_generator.outputs.*;

/**
 * Singleton version of the HealthDataSimulator class for managing health data simulation.
 */
public class HealthDataSimulatorSingleton {

    private static HealthDataSimulatorSingleton instance;
    private int patientCount = 10;
    private ScheduledExecutorService scheduler;
    private OutputStrategy outputStrategy = new ConsoleOutputStrategy();
    private final Random random = new Random();

  
    private HealthDataSimulatorSingleton() {
        
    }

   
    public static synchronized HealthDataSimulatorSingleton getInstance() {
        if (instance == null) {
            instance = new HealthDataSimulatorSingleton();
        }
        return instance;
    }

    public void startSimulation(String[] args) throws IOException {
        parseArguments(args);
        scheduler = Executors.newScheduledThreadPool(patientCount * 4);
        List<Integer> patientIds = initializePatientIds(patientCount);
        Collections.shuffle(patientIds);
        scheduleTasksForPatients(patientIds);
    }

    private void scheduleTasksForPatients(List<Integer> patientIds) {
        ECGDataGenerator ecgDataGenerator = new ECGDataGenerator(patientCount);
        BloodSaturationDataGenerator saturationGen = new BloodSaturationDataGenerator(patientCount);
        BloodPressureDataGenerator pressureGen = new BloodPressureDataGenerator(patientCount);
        BloodLevelsDataGenerator levelsGen = new BloodLevelsDataGenerator(patientCount);
        AlertGenerator alertGen = new AlertGenerator(patientCount);

        for (int patientId : patientIds) {
            scheduleTask(() -> ecgDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.SECONDS);
            scheduleTask(() -> saturationGen.generate(patientId, outputStrategy), 1, TimeUnit.SECONDS);
            scheduleTask(() -> pressureGen.generate(patientId, outputStrategy), 1, TimeUnit.MINUTES);
            scheduleTask(() -> levelsGen.generate(patientId, outputStrategy), 2, TimeUnit.MINUTES);
            scheduleTask(() -> alertGen.generate(patientId, outputStrategy), 20, TimeUnit.SECONDS);
        }
    }

    private void scheduleTask(Runnable task, long period, TimeUnit unit) {
        scheduler.scheduleAtFixedRate(task, random.nextInt(5), period, unit);
    }

    private List<Integer> initializePatientIds(int count) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= count; i++) ids.add(i);
        return ids;
    }

    private void parseArguments(String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--patient-count":
                    if (i + 1 < args.length) {
                        try {
                            patientCount = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid number, using default: 10");
                        }
                    }
                    break;
                case "--output":
                    if (i + 1 < args.length) {
                        String outputArg = args[++i];
                        if (outputArg.equals("console")) {
                            outputStrategy = new ConsoleOutputStrategy();
                        } else if (outputArg.startsWith("file:")) {
                            outputStrategy = new FileOutputStrategy(outputArg.substring(5));
                        } else if (outputArg.startsWith("websocket:")) {
                            outputStrategy = new WebSocketOutputStrategy(Integer.parseInt(outputArg.substring(10)));
                        } else if (outputArg.startsWith("tcp:")) {
                            outputStrategy = new TcpOutputStrategy(Integer.parseInt(outputArg.substring(4)));
                        }
                    }
                    break;
                default:
                    System.err.println("Unknown option: " + args[i]);
            }
        }
    }
}
