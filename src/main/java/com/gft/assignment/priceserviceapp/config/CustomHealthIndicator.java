package com.gft.assignment.priceserviceapp.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean isHealthy = checkSomeService();

        if (isHealthy) {
            return Health.up().withDetail("Custom Service", "Available").build();
        } else {
            return Health.down().withDetail("Custom Service", "Not Available").build();
        }
    }

    private boolean checkSomeService() {
        return true;
    }
}