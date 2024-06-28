package springsamurais.toyswapbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springsamurais.toyswapbackend.exception.ListingFailedToSaveException;
import springsamurais.toyswapbackend.exception.ListingNotFoundException;
import springsamurais.toyswapbackend.model.Listing;
import springsamurais.toyswapbackend.service.ListingServiceImplementation;

import java.util.List;


@RestController
@RequestMapping("api/v1/listings")
public class ListingController {

    @Autowired
    private ListingServiceImplementation listingService;

    @GetMapping("")
    public ResponseEntity<List<Listing>> getAllItems() {
        List<Listing> listingList =listingService.getAllListings();
            return new ResponseEntity<>(listingList, HttpStatus.OK);
    }

    @GetMapping("/{listingID}")
    public ResponseEntity<?> getListingById(@PathVariable("listingID") Long listingID) {
        Listing listingFound;
        try{
            listingFound = listingService.getListingById(listingID);
        } catch (ListingNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listingFound, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveListing(@RequestBody Listing listing) {

        try {
            Listing savedListing = listingService.saveListing(listing);
            return new ResponseEntity<>("Listing added with ID: " + savedListing.getId(), HttpStatus.CREATED);
        }
        catch(ListingFailedToSaveException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public void updateItem(@RequestBody Listing listing) {
    }

    @DeleteMapping
    public void deleteItem(@RequestBody Listing listing) {
    }
}



