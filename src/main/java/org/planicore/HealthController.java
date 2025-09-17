package org.planicore;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up()
                .withDetail("status", "Application is running")
                .withDetail("version", "1.0.0")
                .build();
    }

    @GetMapping("/ready")
    public String ready() {
        return "OK";
    }
}