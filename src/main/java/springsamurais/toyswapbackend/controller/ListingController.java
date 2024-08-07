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
            @RequestParam("userID") String memberId,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("condition") String condition,
            @RequestParam("statusListing") String listingStatus,
            @RequestPart("images") List<MultipartFile> images) {

        ListingDTO dto = new ListingDTO();
        dto.setTitle(title);
        dto.setMemberId(Long.valueOf(memberId));
        dto.setCategory(category);
        dto.setDescription(description);
        dto.setCondition(condition);
        dto.setStatusListing(listingStatus);
        dto.setImageFiles(images);

        return new ResponseEntity<>(listingService.saveListing(dto), HttpStatus.CREATED);

    }


    @PatchMapping("/{listingID}")
    public ResponseEntity<?> updateListing(
            @PathVariable Long listingID,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "condition" , required = false) String condition,
            @RequestParam(value = "statusListing", required = false) String statusListing)
    {
      Listing existingListing = listingService.getListingById(listingID);

      if (title != null) existingListing.setTitle(title);
      if(category != null) existingListing.setCategory(Category.valueOf(category));
      if (description != null) existingListing.setDescription(description);
      if (condition != null) existingListing.setCondition(ItemCondition.valueOf(condition));
      if (statusListing != null) existingListing.setStatusListing(Status.valueOf(statusListing));



        return new ResponseEntity<>(listingService.updateListing(existingListing), HttpStatus.OK);
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


