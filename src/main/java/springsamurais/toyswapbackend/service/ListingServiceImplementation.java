package springsamurais.toyswapbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springsamurais.toyswapbackend.exception.ListingNotFoundException;
import springsamurais.toyswapbackend.exception.ListingFailedToSaveException;
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
    public Listing saveListing(Listing listing) throws ListingFailedToSaveException {

        validateListing(listing);
        try {
            return listingRepository.save(listing);
        } catch (Exception e) {
            throw new ListingFailedToSaveException("Listing failed to save: " + e.getMessage());
        }
    }

    private void validateListing(Listing listing) throws ListingFailedToSaveException {
        if (listing.getTitle() == null || listing.getTitle().isEmpty()) {
            throw new ListingFailedToSaveException("Title cannot be empty");
        }
        if (listing.getMember() == null) {
            throw new ListingFailedToSaveException("Member cannot be empty");
        }
        if (listing.getCategory() == null) {
            throw new ListingFailedToSaveException("Category cannot be empty");
        }
        if (listing.getDescription() == null || listing.getDescription().isEmpty()) {
            throw new ListingFailedToSaveException("Description cannot be empty");
        }
        if (listing.getCondition() == null) {
            throw new ListingFailedToSaveException("Condition cannot be empty");
        }
        if (listing.getStatusListing() == null) {
            throw new ListingFailedToSaveException("Status cannot be empty");
        }
    }



}
