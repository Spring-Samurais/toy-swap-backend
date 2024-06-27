package springsamurais.toyswapbackend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "", nullable = false)
    private User commenter;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "", nullable = false)
    private Listing listing;

    private Date dateCommented;


    public Comment() {
    }

    public Comment(String text, User commenter, Listing listing, Date dateCommented) {

        this.text = text;
        this.commenter = commenter;
        this.listing = listing;
        this.dateCommented = dateCommented;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public Listing getPost() {
        return listing;
    }

    public void setPost(Listing listing) {
        this.listing = listing;
    }

    public Date getDateCommented() {
        return dateCommented;
    }

    public void setDateCommented(Date dateCommented) {
        this.dateCommented = dateCommented;
    }
}
