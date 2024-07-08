package springsamurais.toyswapbackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springsamurais.toyswapbackend.exception.*;
import springsamurais.toyswapbackend.model.*;
import springsamurais.toyswapbackend.service.listing.ListingServiceImplementation;

import java.util.List;

import springsamurais.toyswapbackend.model.Listing;

import javax.annotation.security.PermitAll;


@RestController
@RequestMapping("api/v1/listings")
public class ListingController {

    @Autowired
    private ListingServiceImplementation listingService;


    @GetMapping("")
    public ResponseEntity<List<Listing>> getAllItems() {
        List<Listing> listingList = listingService.getAllListings();
        return new ResponseEntity<>(listingList, HttpStatus.OK);
    }

    @GetMapping("/{listingID}")
    public ResponseEntity<?> getListingById(@PathVariable("listingID") Long listingID) {
        Listing listingFound;

        listingFound = listingService.getListingById(listingID);
        return new ResponseEntity<>(listingFound, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveListing(
            @RequestParam("title") String title,
            @RequestParam("userID") Long memberId,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("condition") String condition,
            @RequestParam("statusListing") String listingStatus,
            @RequestPart("image") List<MultipartFile> images) {

        ListingDTO dto = new ListingDTO();
        dto.setTitle(title);
        dto.setMemberId(memberId);
        dto.setCategory(category);
        dto.setDescription(description);
        dto.setCondition(condition);
        dto.setStatusListing(listingStatus);
        dto.setImageFiles(images);

        return new ResponseEntity<>(listingService.saveListing(dto), HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<?> updateListing(@RequestBody Listing listing) {
        Listing updatedListing = listingService.updateListing(listing);
        return new ResponseEntity<>(updatedListing, HttpStatus.OK);

    }


    @DeleteMapping("/{listingID}")
    public ResponseEntity<?> deleteItem(@PathVariable("listingID") Long listingID) {
        listingService.deleteListingById(listingID);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/member/{memberID}")
    public ResponseEntity<?> deleteItemsByMember(@PathVariable("memberID") Long memberID) throws MemberNotFoundException {

            listingService.deleteListingsByMember(memberID);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}


