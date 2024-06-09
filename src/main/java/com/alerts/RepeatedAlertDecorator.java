package com.alerts;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RepeatedAlertDecorator extends AlertDecorator {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final int interval;
    private final Runnable checkConditionTask;
    private boolean isRunning = false;

    public RepeatedAlertDecorator(Alert decoratedAlert, int intervalInSeconds, Runnable checkConditionTask) {
        super(decoratedAlert);
        this.interval = intervalInSeconds;
        this.checkConditionTask = checkConditionTask;
    }

    public void startRepeatedChecking() {
        if (!isRunning) {
            scheduler.scheduleAtFixedRate(() -> {
                checkConditionTask.run();
                System.out.println("Rechecking alert for " + getPatientId() + " at " + System.currentTimeMillis());
            }, 0, interval, TimeUnit.SECONDS);
            isRunning = true;
        }
    }

    public void stopRepeatedChecking() {
        if (isRunning) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
            isRunning = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public String getCondition() {
        return super.getCondition();
    }

    @Override
    public long getTimestamp() {
        return super.getTimestamp();
    }

    @Override
    public String getPatientId() {
        return super.getPatientId();
    }
}
