package com.alerts.alert_decorators;

import com.alerts.alert_factories.AlertFactory;
import com.alerts.alerts.Alert;

public class PriorityAlertDecorator {
    Alert alert;


    public PriorityAlertDecorator(Alert alert) {
        this.alert = alert;
    }

    public Alert prioritizeAlert() {
        alert.setCondition("PRIORITY: " + alert.getCondition());
        return alert;
    }
}
