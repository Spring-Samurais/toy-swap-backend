package springsamurais.toyswapbackend.service.imgurapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springsamurais.toyswapbackend.model.Listing;
import springsamurais.toyswapbackend.repository.ListingRepository;
import springsamurais.toyswapbackend.service.imgurapi.service.ImgurService;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/images")
public class ImgurController {

    @Autowired
    private ImgurService imgurService;

    @Autowired
    private ListingRepository listingRepository;

    @PostMapping("/upload/{listingId}")
    public ResponseEntity<Listing> uploadImage(@PathVariable Long listingId, @RequestParam("file") MultipartFile file) {
        try {
            Listing listing = listingRepository.findById(listingId)
                    .orElseThrow(() -> new RuntimeException("Listing not found"));

            String imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());
            String imageUrl = imgurService.uploadImage(imageBase64, file.getOriginalFilename());

            listing.setDescription(listing.getDescription() + "\nImage URL: " + imageUrl);

            Listing updatedListing = listingRepository.save(listing);
            return ResponseEntity.ok(updatedListing);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}