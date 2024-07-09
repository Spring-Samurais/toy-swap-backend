package springsamurais.toyswapbackend.service.s3service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springsamurais.toyswapbackend.model.Image;
import springsamurais.toyswapbackend.model.Listing;
import springsamurais.toyswapbackend.service.listing.ListingServiceImplementation;
import springsamurais.toyswapbackend.service.s3service.service.S3Service;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/images")
public class S3Controller {

    @Autowired
    S3Service s3Service;
    @Autowired
    private ListingServiceImplementation listingServiceImplementation;

    @PostMapping("/{listingID}")
    public ResponseEntity<?> uploadImage(
            @PathVariable Long listingID,
            @RequestParam("file") MultipartFile file)
    {

        Listing listing = listingServiceImplementation.getListingById(listingID);

        if (listing == null) {
            return ResponseEntity.notFound().build();
        }

        List<Image> imagesList = new ArrayList<>();

            String url = s3Service.uploadImageS3(file);

            Image image = new Image();
            image.setImageName(file.getOriginalFilename());
            image.setUrl(url);
            image.setListing(listing);
            imagesList.add(image);

        listing.setImages(imagesList);
        s3Service.uploadImageS3(file);
        return new ResponseEntity<>(listing, HttpStatus.ACCEPTED);
    }
}