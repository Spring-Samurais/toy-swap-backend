package springsamurais.toyswapbackend.service.listing;

import org.springframework.web.multipart.MultipartFile;
import springsamurais.toyswapbackend.exception.ListingNotFoundException;
import springsamurais.toyswapbackend.exception.MemberNotFoundException;
import springsamurais.toyswapbackend.model.Listing;
import springsamurais.toyswapbackend.model.ListingDTO;

import java.util.List;

public interface ListingService {
   List<Listing> getAllListings();
   Listing getListingById(Long id);
   Listing saveListing(ListingDTO listing) throws ListingNotFoundException;
   Listing updateListing(Listing listing);
   void deleteListingById(Long listingID) throws ListingNotFoundException;
   void deleteListingsByMember(Long memberID) throws ListingNotFoundException, MemberNotFoundException;

}
