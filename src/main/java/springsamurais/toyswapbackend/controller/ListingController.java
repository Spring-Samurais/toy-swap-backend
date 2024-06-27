package springsamurais.toyswapbackend.controller;

import org.springframework.web.bind.annotation.*;
import springsamurais.toyswapbackend.model.Listing;


@RestController
@RequestMapping
public class ListingController {

    @GetMapping
    public void getAllItems() {
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


