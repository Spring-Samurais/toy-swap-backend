package springsamurais.toyswapbackend.repository;

import org.springframework.data.repository.CrudRepository;
import springsamurais.toyswapbackend.model.Listing;

public interface PostInterface extends CrudRepository<Listing, Long> {}
