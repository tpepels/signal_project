package com.alerts;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A decorator that repeatedly checks an alert condition over time.
 */
public class RepeatedAlertDecorator extends AlertDecorator {

    private final int repeatCount;
    private final long intervalMillis;
    private Timer timer;
    private int currentCount = 0;

    public RepeatedAlertDecorator(AlertInterface alert, int repeatCount, long intervalMillis) {
        super(alert);
        this.repeatCount = repeatCount;
        this.intervalMillis = intervalMillis;
    }

    /**
     * Starts repeated condition checking at fixed intervals.
     */
    public void checkConditionRepeatedly() {
        timer = new Timer(true); // run as daemon
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (currentCount < repeatCount) {
                    System.out.println("Re-check [" + (currentCount + 1) + "/" + repeatCount + "]: " +
                            getCondition());
                    currentCount++;
                } else {
                    timer.cancel();
                }
            }
        }, 0, intervalMillis);
    }

    /**
     * Cancels the repeating task, if active.
     */
    public void cancelChecks() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public String getMessage() {
        return "[REPEATED " + repeatCount + "x] " + super.getMessage();
    }
}
