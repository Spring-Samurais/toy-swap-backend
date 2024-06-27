package springsamurais.toyswapbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springsamurais.toyswapbackend.model.Listing;
import springsamurais.toyswapbackend.service.ListingService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/v1/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @GetMapping("")
    public ResponseEntity<List<Listing>> getAllItems() {
        List<Listing> listingList = new ArrayList<>();
            return new ResponseEntity<>(listingList, HttpStatus.OK);
    }

    @PostMapping
    public void addItem(@RequestBody Listing listing) {
    }

    @PutMapping
    public void updateItem(@RequestBody Listing listing) {
    }

    @DeleteMapping
    public void deleteItem(@RequestBody Listing listing) {
    }
}


