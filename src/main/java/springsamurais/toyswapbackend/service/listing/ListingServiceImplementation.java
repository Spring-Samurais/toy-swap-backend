package springsamurais.toyswapbackend.service.listing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springsamurais.toyswapbackend.exception.ListingNotFoundException;
import springsamurais.toyswapbackend.exception.ListingFailedToSaveException;
import springsamurais.toyswapbackend.exception.MemberNotFoundException;
import springsamurais.toyswapbackend.model.*;
import springsamurais.toyswapbackend.repository.ListingRepository;
import springsamurais.toyswapbackend.service.imgurapi.service.ImgurService;
import springsamurais.toyswapbackend.service.member.MemberService;
import springsamurais.toyswapbackend.service.member.MemberServiceImplementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional// This annotation here point at this class for any transaction with the DB, please refer to Docs for more info :)  -Angel
public class ListingServiceImplementation implements ListingService {

    @Autowired
    ListingRepository listingRepository;
    @Autowired
    MemberServiceImplementation memberService;
    @Autowired
    ImgurService imgurService;

    @Override
    public List<Listing> getAllListings() {
        List<Listing> listingsListResult = new ArrayList<>();
        listingRepository.findAll().forEach(listingsListResult::add);
        return listingsListResult;
    }

    @Override
    public Listing getListingById(Long id) {
        return listingRepository.findById(id).orElseThrow(() -> new ListingNotFoundException("Listing with ID " + id + " not found"));
    }

    @Override
    public Listing saveListing(ListingDTO listingInput) throws ListingFailedToSaveException {
        Listing listing;
        try {
            listing = listingInput.toEntity(memberService.getMemberByID(listingInput.getMemberId()), imgurService);
        } catch (MemberNotFoundException | IOException e) {
            throw new ListingFailedToSaveException("Failed to save the list, reason: " + e.getMessage());
        }

        return listingRepository.save(listing);
    }
    @Override
    public Listing updateListing(Listing listing)  {
        validateListing(listing);

        if (!listingRepository.existsById(listing.getId())) {
            throw new ListingNotFoundException("Listing with ID not found");
        }
        return listingRepository.save(listing);
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


    @Override
    public void deleteListingById(Long listingID) throws ListingNotFoundException {

        Listing listing = listingRepository.findById(listingID).orElseThrow(() -> new ListingNotFoundException("Listing with ID " + listingID + " not found"));
        listingRepository.delete(listing);
    }

    @Override
    public void deleteListingsByMember(Long memberID) throws MemberNotFoundException {
        List<Listing> listings = listingRepository.findByMemberId(memberID);
        if (listings.isEmpty()) {
            throw new MemberNotFoundException("Listing with Member ID " + memberID + " not found");
        }
        listingRepository.deleteAll(listings);
    }


}
