package springsamurais.toyswapbackend.controller.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import springsamurais.toyswapbackend.repository.ListingRepository;

@Component
public class ListingHealthIndicator implements HealthIndicator {

    private final ListingRepository listingRepository;

    public ListingHealthIndicator(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    @Override
    public Health health() {
        long count = listingRepository.count();
        if (count > 0) {
            return Health.up().withDetail("listingCount", count).build();
        }
        return Health.down().withDetail("listingCount", count).build();
    }
}