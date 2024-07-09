package springsamurais.toyswapbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "listing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String title;

    private LocalDateTime datePosted;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Lob
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private ItemCondition condition;

    @Enumerated(EnumType.STRING)
    private Status statusListing;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnoreProperties({"listings","name"})
    @JsonManagedReference
    private Member member;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties({"listings, commenter"})
    @JsonManagedReference
    private List<Comment> comments;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference // Avoid nasty r
    @JsonIgnoreProperties({"id","imageName"})
    private List<Image> images;

}
