package springsamurais.toyswapbackend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springsamurais.toyswapbackend.model.Listing;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface ListingRepository extends CrudRepository<Listing, Long> {

    List<Listing> findByMemberId(Long memberId);


    @Modifying
    @Transactional
    @Query("delete from Listing where id = :listingID")
    void delete(Long listingID);


}




