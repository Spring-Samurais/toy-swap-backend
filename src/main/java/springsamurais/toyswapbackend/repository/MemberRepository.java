package springsamurais.toyswapbackend.repository;

import org.springframework.data.repository.CrudRepository;
import springsamurais.toyswapbackend.model.Member;

public interface MemberRepository extends CrudRepository<Member, Long> {}
