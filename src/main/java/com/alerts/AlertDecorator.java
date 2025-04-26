package com.alerts;

public abstract class AlertDecorator implements AlertStrategy {
    protected AlertStrategy wrappedStrategy;

    public AlertDecorator(AlertStrategy strategy) {
        this.wrappedStrategy = strategy;
    }
}
