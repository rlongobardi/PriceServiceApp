package com.gft.assignment.priceserviceapp.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Perform your custom health check logic here
        boolean isHealthy = checkSomeService(); // Replace with your health check logic

        if (isHealthy) {
            return Health.up().withDetail("Custom Service", "Available").build();
        } else {
            return Health.down().withDetail("Custom Service", "Not Available").build();
        }
    }

    private boolean checkSomeService() {
        // Implement your health check logic
        return true; // or false based on the health check
    }
}