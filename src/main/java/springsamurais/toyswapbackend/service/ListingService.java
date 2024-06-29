package springsamurais.toyswapbackend.service;

import springsamurais.toyswapbackend.exception.ListingNotFoundException;
import springsamurais.toyswapbackend.exception.MemberNotFoundException;
import springsamurais.toyswapbackend.model.Listing;

import java.util.List;

public interface ListingService {
   List<Listing> getAllListings();
   Listing getListingById(Long id);
   Listing saveListing(Listing listing);

   void deleteListingById(Long listingID) throws ListingNotFoundException;
   void deleteListingsByMember(Long memberID) throws ListingNotFoundException, MemberNotFoundException;

}
