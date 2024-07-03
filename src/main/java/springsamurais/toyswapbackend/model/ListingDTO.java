package springsamurais.toyswapbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import springsamurais.toyswapbackend.service.s3service.service.S3Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingDTO {

    private String title;
    private Long memberId;
    private String category;
    private String description;
    private String condition;
    private String statusListing;
    private List<MultipartFile> imageFiles;



    public Listing toEntity(Member member, S3Service s3Service) throws IOException {
        Listing listing = new Listing();

        listing.setTitle(this.title);
        listing.setMember(member);
        listing.setDatePosted(LocalDateTime.now());
        listing.setCategory(Category.valueOf(this.category));
        listing.setDescription(this.description);
        listing.setCondition(ItemCondition.valueOf(this.condition));
        listing.setStatusListing(Status.valueOf(this.statusListing));
        listing.setComments(null);


        List<Image> imagesList = new ArrayList<>();
        //TODO Stream this iteration :D
        for (MultipartFile file : imageFiles) {

            String url = s3Service.uploadFileS3(file);

            Image image = new Image();
            image.setImageName(file.getOriginalFilename());
            image.setUrl(url);
            image.setListing(listing);
            imagesList.add(image);
        }
        listing.setImages(imagesList);

        return listing;
    }
}