package springsamurais.toyswapbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springsamurais.toyswapbackend.exception.ListingNotFoundException;
import springsamurais.toyswapbackend.model.*;
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

    @Override
    public Listing getListingById(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundException("Listing with ID " + id + " not found"));
    }

    @Override
    public void deleteListingById(Long listingID) throws ListingNotFoundException {
        Listing listing = getListingById(listingID);
        listingRepository.delete(listing);
    }

    @Override
    public void deleteListingsByMember(Long memberID) throws ListingNotFoundException {
        List<Listing> listings = listingRepository.findByMemberId(memberID);
        if (listings.isEmpty()) {
            throw new ListingNotFoundException("No listings found for member");
        }
        listingRepository.deleteAll(listings);
    }



}
