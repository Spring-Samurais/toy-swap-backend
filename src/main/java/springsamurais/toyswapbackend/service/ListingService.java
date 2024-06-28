package springsamurais.toyswapbackend.service;

import springsamurais.toyswapbackend.exception.ListingNotFoundException;
import springsamurais.toyswapbackend.model.Listing;

import java.util.List;

public interface ListingService {
   List<Listing> getAllListings();
   Listing getListingById(Long id);

   Listing updateListing(Listing listing) throws ListingNotFoundException;
}
