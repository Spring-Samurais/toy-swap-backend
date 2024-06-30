package springsamurais.toyswapbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
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


    public Listing toEntity(Member member, MultipartFile imageData) throws IOException {
        Listing listing = new Listing();

        listing.setTitle(this.title);
        listing.setMember(member);
        listing.setDatePosted(LocalDateTime.now());
        listing.setPhoto(imageData.getBytes());
        listing.setCategory(Category.valueOf(this.category));
        listing.setDescription(this.description);
        listing.setCondition(ItemCondition.valueOf(this.condition));
        listing.setStatusListing(Status.valueOf(this.statusListing));
        listing.setComments(null);
        return listing;
    }
}