package com.alerts.Decorator;

import com.alerts.Factory.Alert;

import java.util.Timer;
import java.util.TimerTask;

public class RepeatedAlertDecorator extends AlertDecorator {
    private final long interval;

    public RepeatedAlertDecorator(Alert decoratedAlert, long interval) {
        super(decoratedAlert);
        this.interval = interval;
    }

    @Override
    public void triggerAlert() {
        super.triggerAlert();  // Initial trigger
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                RepeatedAlertDecorator.this.decoratedAlert.triggerAlert();
            }
        }, interval, interval);
    }
}