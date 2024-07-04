package springsamurais.toyswapbackend.controller.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import springsamurais.toyswapbackend.repository.MemberRepository;

@Component
public class MemberHealthIndicator implements HealthIndicator {

    private final MemberRepository memberRepository;

    public MemberHealthIndicator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Health health() {
        long count = memberRepository.count();
        if (count > 0) {
            return Health.up().withDetail("memberCount", count).build();
        }
        return Health.down().withDetail("memberCount", count).build();
    }
}