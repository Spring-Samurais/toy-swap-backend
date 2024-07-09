package springsamurais.toyswapbackend.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import springsamurais.toyswapbackend.model.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {

    @Modifying
    @Query("delete from Image where listing.id = :listingId")
    void deleteByListingId(Long listingId);
}
