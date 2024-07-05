package springsamurais.toyswapbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private String text;
    private Long commenterId;
    private Long listingId;

    public Comment toEntity(Member commenter, Listing listing) {
        Comment comment = new Comment();
        comment.setText(this.text);
        comment.setCommenter(commenter);
        comment.setListing(listing);
        comment.setDateCommented(LocalDateTime.now());
        return comment;
    }




}
