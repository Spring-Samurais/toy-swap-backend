package springsamurais.toyswapbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springsamurais.toyswapbackend.model.Listing;

import java.util.List;

@Repository
public interface ListingRepository extends CrudRepository<Listing, Long> {

    List<Listing> findByMemberId(Long memberId);

}




