package springsamurais.toyswapbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springsamurais.toyswapbackend.model.Listing;
import springsamurais.toyswapbackend.repository.ListingRepository;

import java.util.ArrayList;
import java.util.List;


@Service
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
