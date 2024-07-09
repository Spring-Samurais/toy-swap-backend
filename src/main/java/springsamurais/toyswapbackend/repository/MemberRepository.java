package springsamurais.toyswapbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springsamurais.toyswapbackend.model.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByUsername(String nickname);
}
