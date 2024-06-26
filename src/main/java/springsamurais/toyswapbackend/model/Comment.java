package springsamurais.toyswapbackend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
//@Table(name = "comments")
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
    private Post post;

    private Date dateCommented;


    public Comment() {
    }

    public Comment(String text, User commenter, Post post, Date dateCommented) {

        this.text = text;
        this.commenter = commenter;
        this.post = post;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getDateCommented() {
        return dateCommented;
    }

    public void setDateCommented(Date dateCommented) {
        this.dateCommented = dateCommented;
    }
}
