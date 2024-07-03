package springsamurais.toyswapbackend.controller.health;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@AllArgsConstructor
public class HealthController {

    private final HealthEndpoint healthEndpoint;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //TODO We need to add more health points . S3 , any other API and services we use >:-(
    @GetMapping("/health")
    public HealthComponent getHealth() {
        return healthEndpoint.health();
    }

    @GetMapping("/test-db-connection")
    public String testDbConnection() {
        try {
            jdbcTemplate.execute("SELECT * FROM LISTING");
            return "Database connection is successful!";
            //TODO : Proper error handling innit?
        } catch (Exception e) {
            return "Failed to connect to the database: " + e.getMessage();
        }
    }
}

