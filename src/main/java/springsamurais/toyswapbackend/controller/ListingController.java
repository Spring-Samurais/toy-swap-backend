package springsamurais.toyswapbackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springsamurais.toyswapbackend.exception.*;
import springsamurais.toyswapbackend.exception.ListingFailedToSaveException;
import springsamurais.toyswapbackend.exception.ListingNotFoundException;
import springsamurais.toyswapbackend.model.*;
import springsamurais.toyswapbackend.service.listing.ListingServiceImplementation;
import java.util.List;
import springsamurais.toyswapbackend.exception.*;
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
        List<Listing> listingList = listingService.getAllListings();
        return new ResponseEntity<>(listingList, HttpStatus.OK);
    }

    @GetMapping("/{listingID}")
    public ResponseEntity<?> getListingById(@PathVariable("listingID") Long listingID) {
        Listing listingFound;
        try {
            listingFound = listingService.getListingById(listingID);
        } catch (ListingNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listingFound, HttpStatus.OK);
    }
    //Might delete later
    @GetMapping("/{imageID}/image")
    public ResponseEntity<byte[]>  getImageById(@PathVariable("imageID") Long imageID) {
        Listing listing = listingService.getListingById(imageID);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(listing.getPhoto());
    }

    @PostMapping
    public ResponseEntity<?> saveListing(
            @RequestParam("title") String title ,
            @RequestParam("userID") Long memberId,
            @RequestParam("category") String category,
            @RequestParam("description")  String description,
            @RequestParam("condition") String condition,
            @RequestParam("statusListing") String listingStatus,
            @RequestPart("image") MultipartFile image) {

        ListingDTO dto = new ListingDTO();
        dto.setTitle(title);
        dto.setMemberId(memberId);
        dto.setCategory(category);
        dto.setDescription(description);
        dto.setCondition(condition);
        dto.setStatusListing(listingStatus);

        try {
            return new ResponseEntity<>(listingService.saveListing(dto,image),HttpStatus.CREATED);
        } catch (ListingFailedToSaveException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateListing(@RequestBody Listing listing) {
        try {
            Listing updatedListing = listingService.updateListing(listing);
            return new ResponseEntity<>(updatedListing, HttpStatus.OK);
        } catch (ListingNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (InvalidListingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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


