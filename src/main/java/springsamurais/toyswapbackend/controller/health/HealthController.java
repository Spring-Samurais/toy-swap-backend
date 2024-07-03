package springsamurais.toyswapbackend.controller.health;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.actuate.health.HealthEndpoint;

@RestController
@AllArgsConstructor
public class HealthController {

    private final HealthEndpoint healthEndpoint;

    @GetMapping("/health")
    public HealthComponent getHealth() {
        return healthEndpoint.health();
    }
}

