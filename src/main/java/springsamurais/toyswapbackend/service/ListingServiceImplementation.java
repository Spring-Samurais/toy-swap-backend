package springsamurais.toyswapbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springsamurais.toyswapbackend.model.Listing;
import springsamurais.toyswapbackend.repository.ListingRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional // This annotation here point at this class for any transaction with the DB, please refer to Docs for more info :)  -Angel
public class ListingServiceImplementation implements ListingService {

    @Autowired
    ListingRepository listingRepository;

    @Override
    public List<Listing> getAllListings() {
        List<Listing> listingsListResult = new ArrayList<>();
        listingRepository.findAll().forEach(listingsListResult::add);
        return listingsListResult;
    }

}
