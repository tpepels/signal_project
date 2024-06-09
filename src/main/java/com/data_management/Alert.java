package com.data_management;

// Component Interface
interface Alert {
    void sendAlert();
}

// Concrete Component
class BasicAlert implements Alert {
    public void sendAlert() {
        System.out.println("Basic Alert Sent");
    }
}

// Decorator Base Class
abstract class AlertDecorator implements Alert {
    protected Alert decoratedAlert;

    public AlertDecorator(Alert alert) {
        this.decoratedAlert = alert;
    }

    public void sendAlert() {
        decoratedAlert.sendAlert();
    }
}

// Concrete Decorators
class RepeatedAlertDecorator extends AlertDecorator {
    public RepeatedAlertDecorator(Alert alert) {
        super(alert);
    }

    public void sendAlert() {
        super.sendAlert();
        // Additional functionality for repeated alerts
    }
}

class PriorityAlertDecorator extends AlertDecorator {
    public PriorityAlertDecorator(Alert alert) {
        super(alert);
    }

    public void sendAlert() {
        // Increase priority
        super.sendAlert();
    }
}
