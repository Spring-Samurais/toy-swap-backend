package springsamurais.toyswapbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String title;

    @Lob
    private byte[] photo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonBackReference
    private Member member;

    private LocalDateTime datePosted;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private ItemCondition condition;

    @Enumerated(EnumType.STRING)
    private Status statusListing;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
