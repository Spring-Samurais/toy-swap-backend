package springsamurais.toyswapbackend.service;

import springsamurais.toyswapbackend.model.Listing;

import java.util.List;

public interface ListingService {
   List<Listing> getAllListings();
   Listing getListingById(Long id);
}
