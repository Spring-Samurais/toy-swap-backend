package springsamurais.toyswapbackend.controller.health;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.actuate.health.HealthEndpoint;
import springsamurais.toyswapbackend.repository.ListingRepository;
import springsamurais.toyswapbackend.repository.MemberRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@AllArgsConstructor
public class HealthController {

    private final HealthEndpoint healthEndpoint;

    private final DataSource dataSource;

    private final CacheManager cacheManager;

    @Autowired
    private ListingHealthIndicator listingHealthIndicator;

    @Autowired
    private MemberHealthIndicator memberHealthIndicator;

    @GetMapping("/health/listings")
    public Health getListingHealth() {
        return listingHealthIndicator.health();
    }

    @GetMapping("/health/members")
    public Health getMemberHealth() {
        return memberHealthIndicator.health();
    }

    @GetMapping("/health/all")
    public HealthComponent getHealth() {
        return healthEndpoint.health();
    }

    @GetMapping("/health/allcon")
    public ResponseEntity<String> checkConnectionHealth() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) {
                return ResponseEntity.ok("Connection is valid and Application is up and running");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error and Application is down");
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error and Application is down");
        }
    }

    @GetMapping("/health/cache")
    public ResponseEntity<String> checkCacheHealth() {
        try {
            Cache cacheMember = cacheManager.getCache("members" );
            Cache cacheListings = cacheManager.getCache("listings");
            if (cacheMember != null && cacheListings != null) {
                return ResponseEntity.ok("Cache is working and Application is up and running");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error and Application is down");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error and Application is down");
        }
    }
}


