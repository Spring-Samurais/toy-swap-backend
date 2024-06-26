package springsamurais.toyswapbackend.model;

import jakarta.persistence.*;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Entity
//@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private Blob photo;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "", nullable = false)
    private User poster;

    private Date datePosted;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String condition;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "")
    private List<Comment> comments;

    public Post() {
    }

    public Post( Blob photo, User poster, Date datePosted, Category category, String description, String condition, Status status, List<Comment> comments) {

        this.photo = photo;
        this.poster = poster;
        this.datePosted = datePosted;
        this.category = category;
        this.description = description;
        this.condition = condition;
        this.status = status;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
