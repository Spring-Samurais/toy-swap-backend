package springsamurais.toyswapbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springsamurais.toyswapbackend.exception.*;
import springsamurais.toyswapbackend.model.Listing;
import springsamurais.toyswapbackend.service.ListingService;
import springsamurais.toyswapbackend.service.ListingServiceImplementation;

import java.util.ArrayList;
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
    public void addItem(@RequestBody Listing listing) {
    }

    @PutMapping
    public void updateItem(@RequestBody Listing listing) {
    }


    @DeleteMapping("/{listingID}")
    public ResponseEntity<?> deleteItem(@PathVariable("listingID") Long listingID) {
        try {
            listingService.deleteListingById(listingID);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ListingNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/member/{memberID}")
    public ResponseEntity<?> deleteItemsByMember(@PathVariable("memberID") Long memberID) {
        try {
            listingService.deleteListingsByMember(memberID);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ListingNotFoundException | MemberNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}


