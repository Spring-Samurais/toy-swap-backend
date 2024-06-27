package springsamurais.toyswapbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springsamurais.toyswapbackend.model.Listing;

@Repository
public interface ListingRepository extends CrudRepository<Listing, Long> {}
